<template>
  <div class="row bg-primary bg-opacity-25 mb-3 py-3" id="commentTable">
    <div v-for="comment in comments" class="border-bottom border-dark mb-2 py-2">
      <div v-text="comment.createdDate" class="mb-1"></div>
      <div v-text="comment.content"></div>
    </div>
    <form  v-on:submit.prevent="postComment" class="row pt-2" method="post" name="commentForm" id="commentForm" >
      <div class="col-11">
        <input v-model="content" class="form-control" style="height:80px;" type="text" name="commentContent" id="commentContent" required placeholder="댓글을 입력해 주세요."/>
      </div>
      <button class="col-1 btn btn-primary btn-lg" type="submit" >등록</button>
    </form>
  </div>
</template>

<script>

export default {
  name: "Comments",
  props: ['boardId'],
  data() {
    return {
      comments: Array,
      content: ''
    }
  },
  methods: {
    getComments() {
      this.$axios.get(`http://localhost:9090/boards/${this.boardId}/comments`)
          .then(response => {
            const data = response;
            this.comments= data.data;
          })
          .catch(error => {
            console.log(error)
          });
    },
    postComment(){
      let data = {};
      data.content = this.content;
      this.$axios.post(`http://localhost:9090/boards/${this.boardId}/comments`, data)
          .then(response => {
            console.log(response);
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => {
            this.getComments();
          })
    }
  },
  created() {
    this.getComments()
  }
}
</script>

<style scoped>

</style>