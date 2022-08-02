<template>
  <div class="container mt-5">
    <form v-on:submit.prevent="passwordCheck" name="passwordConfirmForm" id="passwordConfirmForm">
      <table class="table text-center">
        <tr>
          <th class="table-primary">비밀번호</th>
          <td>
            <input v-model="password" class="form-control" type="password" name="password" id="password"
                   placeholder="비밀번호" required/>
          </td>
        </tr>

      </table>

      <div class="row">
        <div class="col text-end">
          <button v-on:click="getBoardView" class="btn btn-lg btn-danger" type="button">취소</button>
        </div>
        <div class="col text-start">
          <button class="btn btn-lg btn-primary" type="submit">확인</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "PasswordCheckView",
  data() {
    return {
      password: '',
      type: this.$route.query.type,
      boardId: this.$route.params.id,
      result: ''
    }
  },
  methods: {
    getBoardView() {
      this.$router.push({
        path: `/board/${this.boardId}`,
        query: {
          curPage: this.$route.query.curPage,
          createdDateFrom: this.$route.query.createdDateFrom,
          createdDateTo: this.$route.query.createdDateTo,
          categoryId: this.$route.query.categoryId,
          text: this.$route.query.text
        }
      });
    },
    getListView() {
      this.$router.push({
        path: "/list",
        query: {
          curPage: this.$route.query.curPage,
          createdDateFrom: this.$route.query.createdDateFrom,
          createdDateTo: this.$route.query.createdDateTo,
          categoryId: this.$route.query.categoryId,
          text: this.$route.query.text
        }
      });
    },
    passwordCheck() {
      const formData = new FormData();
      formData.append('password', this.password);
      this.$axios.post(`http://localhost:9090/boards/${this.boardId}/password-check`, formData)
          .then(response => {
            const data = response;
            console.log(response)
            this.result = data.data.result;
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => {
            if (this.result == "success") {
              if (this.type == "modify") {
                this.getModifyView();
              }

              if (this.type == "delete") {
                this.deleteBoard();
              }
            } else {
              alert("비밀번호가 다릅니다!");
            }
          })
    },
    getModifyView() {
      this.$router.push({
        path: `/board/${this.boardId}/modify`,
        query: {
          curPage: this.$route.query.curPage,
          createdDateFrom: this.$route.query.createdDateFrom,
          createdDateTo: this.$route.query.createdDateTo,
          categoryId: this.$route.query.categoryId,
          text: this.$route.query.text
        }
      });
    },
    deleteBoard() {
      this.$axios.delete(`http://localhost:9090/boards/${this.boardId}`)
          .then(response => {
            console.log(response);
            alert("게시글이 삭제되었습니다!");
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => {
            this.getListView();
          })
    }
  }
}
</script>

<style scoped>

</style>