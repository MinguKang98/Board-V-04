<template>
  <div class="container mt-5">
    <form v-on:submit.prevent="modifyBoard" name="modifyForm" id="modifyForm" enctype="multipart/form-data">
      <table class="table">
        <tr>
          <th class="table-primary pl-3">카테고리</th>
          <td>
            <span v-text="category.name" name="category" id="category"></span>
          </td>
        </tr>
        <tr>
          <th class="table-primary pl-3">등록 일시</th>
          <td>
            <span v-text="board.createdDate" name="createdDate" id="createdDate"></span>
          </td>
        </tr>
        <tr>
          <th class="table-primary pl-3">수정 일시</th>
          <td>
            <span v-if="board.updatedDate === null" v-text="'-'" name="updatedDate" id="updatedDate"></span>
            <span v-else v-text="board.updatedDate" name="updatedDate" id="updatedDate"></span>
          </td>
        </tr>
        <tr>
          <th class="table-primary pl-3">조회수</th>
          <td>
            <span v-text="board.visitCount" name="count" id="count"></span>
          </td>
        </tr>
        <tr>
          <th class="table-primary pl-3">작성자</th>
          <td>
            <input v-if="user === undefined" v-bind:value="board.user" v-on:input="user=$event.target.value"
                   class="form-control w-50" type="text" name="user" id="user" required minlength="3" maxlength="5"/>
            <input v-else v-bind:value="user" v-on:input="user=$event.target.value" class="form-control w-50"
                   type="text" name="user" id="user" required minlength="3" maxlength="5"/>
            <span v-if="error.user === ''" id="userWarning"></span>
            <span v-else v-text="error.user" style="color: red" id="userWarning"></span>
          </td>
        </tr>
        <tr>
          <th class="table-primary pl-3">비밀번호</th>
          <td>
            <input v-on:input="password=$event.target.value" class="form-control w-50" type="password" name="password"
                   id="password" placeholder="비밀번호" required
                   pattern="^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{4,15}$"/>
            <span v-if="error.password === ''" id="passwordWarning"></span>
            <span v-else v-text="error.password" style="color: red" id="passwordWarning"></span>
          </td>
        </tr>
        <tr>
          <th class="table-primary pl-3">제목</th>
          <td>
            <input v-if="title === undefined" v-bind:value="board.title" v-on:input="title=$event.target.value"
                   class="form-control" type="text" name="title" id="title" required minlength="4" maxlength="100"/>
            <input v-else v-bind:value="title" v-on:input="title=$event.target.value" class="form-control" type="text"
                   name="title" id="title" required minlength="4" maxlength="100"/>
            <span v-if="error.title === ''" id="titleWarning"></span>
            <span v-else v-text="error.title" style="color: red" id="titleWarning"></span>
          </td>
        </tr>
        <tr>
          <th class="table-primary pl-3">내용</th>
          <td>
            <textarea  v-if="content === undefined" v-bind:value="board.content"  v-on:input="content=$event.target.value"
                      class="form-control" style="height:300px; resize: none;" name="content" id="content" required
                      minlength="4" maxlength="2000">{{board.content}}</textarea>
            <textarea v-else v-bind:value="content" v-on:input="content=$event.target.value" class="form-control"
                      style="height:300px; resize: none;" name="content" id="content" required minlength="4"
                      maxlength="2000"></textarea>
            <span v-if="error.title === ''" id="contentWarning"></span>
            <span v-else v-text="error.title" style="color: red" id="contentWarning"></span>
          </td>
        </tr>
        <tr>
          <th class="table-primary pl-3">파일첨부</th>
          <td>

            <div v-for="n in 3">
              <div v-if="n <= files.length" class="my-2">
                <i class="fas fa-paperclip"></i>&nbsp;<span v-text="files[n-1].originName"></span>&nbsp;
                <button v-on:click="downloadFile(files[n-1].fileId, files[n-1].originName)" class="btn btn-secondary"
                        type="button">
                  Download
                </button>
                <button v-on:click="deleteFile(files[n-1], n-1)" class="btn btn-secondary" type="button">X</button>
              </div>
              <div v-else class="my-2">
                <input v-on:change="addFile" class="form-control w-50" type="file"/>
              </div>
            </div>

          </td>
        </tr>
      </table>

      <div class="row">
        <div class="col text-start">
          <button v-on:click="getBoardView" class="btn btn-danger btn-lg" type="button">
            취소
          </button>
        </div>
        <div class="col text-end">
          <button class="btn btn-primary btn-lg" type="submit">저장</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "ModifyView",
  data() {
    return {
      boardId: this.$route.params.id,
      board: Object,
      category: Object,
      files: [],
      user: undefined,
      password: '',
      title: undefined,
      content: undefined,
      deleteFileList: [],
      addFileList: [],
      error: {
        password: '',
      }
    }
  },
  methods: {
    getBoard() {
      this.$axios.get(`http://localhost:9090/boards/${this.boardId}`)
          .then(response => {
            const data = response;
            this.board = data.data;
            console.log(response);
          })
          .catch(error => {
            console.log(error)
          })
          .finally(() => {
            this.getCategory(Number(this.board.categoryId));
          })
    },
    getCategory(val) {
      this.$axios.get(`http://localhost:9090/categories/${val}`)
          .then(response => {
            const data = response;
            this.category = data.data;
            console.log(response);
          })
          .catch(error => {
            console.log(error)
          });
    },
    getFiles() {
      this.$axios.get(`http://localhost:9090/boards/${this.boardId}/files`)
          .then(response => {
            const data = response;
            this.files = data.data;
          })
          .catch(error => {
            console.log(error)
          });
    },
    downloadFile(fileId, fileName) {
      this.$axios.get(`http://localhost:9090/files/${fileId}`, {responseType: "blob"})
          .then(response => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
            console.log(response)
          })
          .catch(error => {
            console.log(error)
          });
    },
    deleteFile(file, index) {
      this.files.splice(index, 1);
      this.deleteFileList.push(file.fileId);

    },
    addFile(e) {
      this.addFileList.push(e.target.files[0]);
    },
    clearError() {
      this.error.password = '';
    },
    checkForm() {
      this.clearError();
      const regExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{4,15}$/;
      if (regExp.test(this.password) == false) {
        this.error.password = "4글자 이상, 16글자 미만인 영문/숫자/특수문자의 조합";
        return false;
      }
      return true;
    },
    modifyBoard() {
      if (this.checkForm() === false) {
        return false;
      }
      const formData = new FormData();
      (this.user === undefined) ? formData.append('user', this.board.user) : formData.append('user', this.user);
      (this.title === undefined) ? formData.append('title', this.board.title) : formData.append('title', this.title);
      (this.content === '') ? formData.append('content', this.board.content) : formData.append('content', this.content);
      formData.append('password', this.password);
      for (let i = 0; i < this.addFileList.length; i++) {
        formData.append('fileList', this.addFileList[i]);
      }
      formData.append('deleteFileList', this.deleteFileList);

      this.$axios.put(`http://localhost:9090/boards/${this.boardId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
          .then(response => {
            console.log(response)
            const result = response.data.result;
            if (result == "success") {
              this.getBoardView();
            }

            if (result === "fail") {
              alert("비밀번호가 일치하지 않습니다!");
            }
          })
          .catch(error => {
            console.log(error)
          });
    },
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
  },
  mounted() {
    this.getBoard();
    this.getFiles();
  }
}
</script>

<style scoped>

</style>