<template>
  <form v-on:submit.prevent="saveBoard" method="post" name="writeForm" id="writeForm" enctype="multipart/form-data">
    <table class="table">
      <tr>
        <th class="table-primary pl-3 ">카테고리</th>
        <td>
          <select v-model="categoryId" class="form-select w-50" name="category" id="category">
            <option value="0" selected>카테고리 선택</option>
            <option v-for="category in categories" v-bind:value="category.categoryId" v-text="category.name"></option>
          </select>
          <span v-if="error.categoryId === ''" id="categoryWarning"></span>
          <span v-else v-text="error.categoryId" style="color: red" id="categoryWarning"></span>
        </td>
      </tr>
      <tr>
        <th class="table-primary pl-3 ">작성자</th>
        <td>
          <input v-model="user" class="form-control w-50" type="text" name="user" id="user" required minlength="3"
                 maxlength="5"/>
          <span v-if="error.user === ''" id="userWarning"></span>
          <span v-else v-text="error.user" style="color: red" id="userWarning"></span>
        </td>
      </tr>
      <tr>
        <th class="table-primary pl-3 ">비밀번호</th>
        <td>
          <input v-model="password" class="form-control w-50 my-2" type="password" name="password" id="password"
                 placeholder="비밀번호" required
                 pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{4,15}$"/>
          <input v-model="passwordCheck" class="form-control w-50 my-2" type="password" name="passwordCheck"
                 id="passwordCheck" placeholder="비밀번호 확인" required/>
          <span v-if="error.password === ''" id="passwordWarning"></span>
          <span v-else v-text="error.password" style="color: red" id="passwordWarning"></span>
        </td>
      </tr>
      <tr>
        <th class="table-primary pl-3 ">제목</th>
        <td>
          <input v-model="title" class="form-control" type="text" name="title" id="title" required minlength="4" maxlength="100"/>
          <span v-if="error.title === ''" id="titleWarning"></span>
          <span v-else v-text="error.title" style="color: red" id="titleWarning"></span>
        </td>
      </tr>
      <tr>
        <th class="table-primary pl-3 ">내용</th>
        <td>
          <textarea v-model="content" class="form-control" style="height:300px; resize: none;" name="content"
                    id="content" required minlength="4" maxlength="2000"></textarea>
          <span v-if="content.title === ''" id="contentWarning"></span>
          <span v-else v-text="content.title" style="color: red" id="contentWarning"></span>
        </td>
      </tr>
      <tr>
        <th class="table-primary pl-3 ">파일첨부</th>
        <td>
          <div class="my-2">
            <input v-on:change="selectFile" class="form-control w-50" type="file" name="file1" id="file1"/>
          </div>
          <div class="my-2">
            <input v-on:change="selectFile" class="form-control w-50" type="file" name="file2" id="file2"/>
          </div>
          <div class="my-2">
            <input v-on:change="selectFile" class="form-control w-50" type="file" name="file3" id="file3"/>
          </div>
        </td>
      </tr>
    </table>

    <div class="row">
      <div class="col text-start">
        <button v-on:click="getListView" class="btn btn-danger btn-lg" type="button">취소</button>
      </div>
      <div class="col text-end">
        <button class="btn btn-primary btn-lg" type="submit">저장</button>
      </div>
    </div>
  </form>
</template>

<script>
export default {
  name: "WriteBoard",
  data() {
    return {
      categories: Array,
      categoryId: 0,
      user: '',
      password: '',
      passwordCheck: '',
      title: '',
      content: '',
      fileList: [],
      error: {
        categoryId: '',
        user: '',
        password: '',
        title: '',
        content: ''
      }
    }
  },
  methods: {
    getCategories() {
      this.$axios.get("http://localhost:9090/categories")
          .then(response => {
            const data = response;
            this.categories = data.data;
          })
          .catch(error => {
            console.log(error)
          });
    },
    selectFile(e) {
      this.fileList.push(e.target.files[0]);
    },
    clearError() {
      this.error.categoryId = '';
      this.error.user = '';
      this.error.password = '';
      this.error.title = '';
      this.error.content = '';
    },
    checkForm() {
      this.clearError();

      if (this.categoryId == 0) {
        this.error.categoryId = '카테고리를 선택해주세요';
        return false;
      }

      if (this.user.length < 3 || this.user.length >= 5) {
        this.error.user = "3글자 이상, 5글자 미만";
        return false;
      }

      const regExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{4,15}$/;
      if (regExp.test(this.password) == false) {
        this.error.password = "4글자 이상, 16글자 미만인 영문/숫자/특수문자의 조합";
        return false;
      }

      if (this.password != this.passwordCheck) {
        this.error.password = "비밀번호와 비밀번호 확인이 일치하지 않습니다";
        return false;
      }

      if (this.title.length < 4 || this.title.length >= 1000) {
        this.error.title = "4글자 이상, 100글자 미만";
        return false;
      }

      if (this.content.length < 4 || this.content.length >= 2000) {
        this.error.content = "4글자 이상, 2000글자 미만";
        return false;
      }

      return true;
    },
    saveBoard() {
      if (this.checkForm() === false) {
        return false;
      }
      const formData = new FormData();
      formData.append('user', this.user);
      formData.append('password', this.password);
      formData.append('title', this.title);
      formData.append('content', this.content);
      formData.append('categoryId', this.categoryId);
      for (let i = 0; i < this.fileList.length; i++) {
        formData.append('fileList', this.fileList[i]);
      }

      console.log(this.fileList)
      console.log(this.title)
      this.$axios.post("http://localhost:9090/boards", formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
          .then(response => {
            console.log(response)
            const boardId = response.data;
            this.getBoardView(boardId);
          })
          .catch(error => {
            console.log(error)
          });
    },
    getListView() {
      this.$router.push({
        path: `/list`,
        query: {
          curPage: this.$route.query.curPage,
          createdDateFrom: this.$route.query.createdDateFrom,
          createdDateTo: this.$route.query.createdDateTo,
          categoryId: this.$route.query.categoryId,
          text: this.$route.query.text
        }});
    },
    getBoardView(val) {
      this.$router.push({
        path: `/board/${val}`});
    },
  },
  created() {
    this.getCategories()
  }
}
</script>

<style scoped>

</style>