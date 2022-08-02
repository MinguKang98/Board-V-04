<template>
  <div>
    <div class="row mb-3">
      <span v-text="board.user" class="col-6"></span>
      <span v-text="board.createdDate" class="col-3 text-center"></span>
      <span v-if="board.updatedDate === null" v-text="'-'" class="col-3 text-center"></span>
      <span v-text="board.updatedDate" class="col-3 text-center"></span>
    </div>
    <div class="row pb-3 border-bottom border-4 ">
      <h2 v-text="`[${category.name}]   ${board.title}`" class="col-10"></h2>
      <span v-text="`조회수: ${board.visitCount}`" class="col align-bottom"></span>
    </div>
    <br>

    <!--본문-->
    <div class="row px-1 py-3 mx-3 mb-3 border border-primary">
      <p v-text="board.content" ></p>
    </div>
  </div>
</template>

<script>

export default {
  name: "BoardMain",
  props: ['boardId'],
  data() {
    return {
      board: Object,
      category: Object
    }
  },
  methods: {
    getBoard() {
      this.$axios.get(`http://localhost:9090/boards/${this.boardId}`)
          .then(response => {
            const data = response;
            this.board = data.data;
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => {
            this.getCategory(this.board.categoryId)
          })
    },

    getCategory(val) {
      this.$axios.get(`http://localhost:9090/categories/${val}`)
          .then(response => {
            const data = response;
            this.category = data.data;
          })
          .catch(error => {
            console.log(error)
          });
    },
  },
  created() {
    this.getBoard()
  }
}
</script>

<style scoped>

</style>