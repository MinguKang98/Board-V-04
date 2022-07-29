<template>
  <div>

    <div class="container">
      <form @submit.prevent="submitForm" class="row" name="searchForm" id="searchForm">
        <div class="col-5">
          <div class="row">
            <div class="col-2 align-middle">등록일</div>
            <div class="col-4">
              <input v-model="createdDateFrom" class="form-control" type="date" name="createdDateFrom"
                     id="createdDateFrom"/>
            </div>
            <div class="col-1 align-middle"> ~</div>
            <div class="col-4">
              <input v-model="createdDateTo" class="form-control" type="date" name="createdDateTo"
                     id="createdDateTo"/>
            </div>
          </div>
        </div>
        <div class="col-2">
          <select v-model="categoryId" class="form-select" name="categoryId" id="categoryId">
            <option value="0" selected>전체 카테고리</option>
            <option v-for="category in categories" v-bind:value="category.categoryId"> {{ category.name }}</option>
          </select>
        </div>
        <div class="col-4">
          <input v-model="text" class="form-control" type="text" name="text" id="text"
                 placeholder="검색어를 입력해 주세요. (제목+작성자+내용)"/>
        </div>
        <div class="col-1">
          <button class="btn btn-outline-primary float-right" type="submit">검색</button>
        </div>
      </form>
    </div>
    <br>

    <div class="container">
      <div class="row">
        <label>총 {{ totalBoardCount }}건</label>
      </div>
      <br>
      <table class="table">
        <thead class="table-light">
        <tr>
          <th>카테고리</th>
          <th></th>
          <th>제목</th>
          <th>작성자</th>
          <th>조회수</th>
          <th>등록 일시</th>
          <th>수정 일시</th>
        </tr>
        </thead>
        <tbody>
          <tr v-for="board in boards">
            <td>{{ categories[board.categoryId - 1].name }}</td>
            <td v-if="board.fileExist === true"><i class="fas fa-paperclip"></i></td>
            <td v-else></td>
            <td>
              <a v-on:click="getBoardView(board.boardId)">
                <span>{{board.title}} ({{board.commentCount}})</span>
              </a>
            </td>
            <td v-text="board.user"></td>
            <td v-text="board.visitCount"></td>
            <td v-text="board.createdDate"></td>
            <td v-if="board.updatedDate === null" v-text="'-'"></td>
            <td v-else v-text="board.updatedDate"></td>
          </tr>
        </tbody>
      </table>

      <br>
      <br>

      <nav>
        <ul class="pagination justify-content-center">
          <li v-on:click="pageChange(1)" class="page-item">
            <a class="page-link">
              <i class="fas fa-chevron-left"></i><i class="fas fa-chevron-left"></i>
            </a>
          </li>
          <li v-on:click="pageChange(prevPage)" class="page-item">
            <a class="page-link">
              <i class="fas fa-chevron-left"></i>
            </a>
          </li>
          <li v-for="page in pages" class="page-item">
            <a v-if="curPage === page" v-text="page" href="#"  class="page-link active" ></a>
            <a v-else v-text="page" v-on:click="pageChange(page)"  class="page-link" ></a>
          </li>
          <li v-on:click="pageChange(nextPage)" class="page-item">
            <a class="page-link">
              <i class="fas fa-chevron-right"></i>
            </a>
          </li>
          <li v-on:click="pageChange(totalPageCount)" class="page-item">
            <a class="page-link" >
              <i class="fas fa-chevron-right"></i><i class="fas fa-chevron-right"></i>
            </a>
          </li>
        </ul>
      </nav>

    </div>
  </div>
</template>

<script>

export default {
  name: "List",
  data() {
    return {
      categories: [],
      curPage: (this.$route.query.curPage == null) ? 1 : Number(this.$route.query.curPage),
      createdDateFrom: this.$route.query.createdDateFrom,
      createdDateTo: this.$route.query.createdDateTo,
      categoryId: (this.$route.query.categoryId == null) ? 0 : Number(this.$route.query.categoryId),
      text: this.$route.query.text,
      boards: [],
      totalBoardCount: 0
    }
  },
  computed: {
    totalPageCount() {
      return Math.floor(this.totalBoardCount / 10) +1;
    },
    firstPage() {
      return Math.floor(this.curPage / 11) * 10 + 1;
    },
    lastPage() {
      let lastPage = this.firstPage + 9
      return lastPage <= this.totalPageCount ? lastPage : this.totalPageCount

    },
    prevPage() {
      return (this.curPage - 10 < 0) ? 1 : this.curPage - 10;
    },
    nextPage() {
      return (this.curPage + 10 > this.totalPageCount) ? this.totalPageCount : this.curPage + 10;
    },
    pages() {
      const list =[];
      for (let i = this.firstPage; i <= this.lastPage; i += 1) {
        list.push(i);
      }
      return list;
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
    getTotalBoardCount() {
      this.$axios.get("http://localhost:9090/boards/count",{
        params: {
          curPage: this.curPage,
          createdDateFrom: this.createdDateFrom,
          createdDateTo: this.createdDateTo,
          categoryId: this.categoryId,
          text: this.text
        }})
          .then(response => {
            const data = response;
            this.totalBoardCount = data.data;
          })
          .catch(error => {
            console.log(error)
          });
    },
    getBoards() {
      this.$axios.get("http://localhost:9090/boards", {
        params: {
          curPage: this.curPage,
          createdDateFrom: this.createdDateFrom,
          createdDateTo: this.createdDateTo,
          categoryId: this.categoryId,
          text: this.text
        }})
          .then(response => {
            const data = response;
            this.boards = data.data;
          })
          .catch(error => {
            console.log(error)
          });
    },
    pageChange(val) {
      this.curPage = val;
      this.getBoards();
      this.changeUrl();
    },
    submitForm() {
      this.curPage = 1;
      this.getBoards();
      this.getTotalBoardCount();
      this.changeUrl();
    },
    getBoardView(val) {
      this.$router.push({
        path: `/board/${val}`,
        query: {
          curPage: this.curPage,
          createdDateFrom: this.createdDateFrom,
          createdDateTo: this.createdDateTo,
          categoryId: this.categoryId,
          text: this.text
        }});
    },
    changeUrl() {
      this.$router.push({
        path: '/list', query: {
          curPage: this.curPage,
          createdDateFrom: this.createdDateFrom,
          createdDateTo: this.createdDateTo,
          categoryId: this.categoryId,
          text: this.text
        }});
      }
    },
    created() {
          this.getCategories(),
          this.getBoards(),
          this.getTotalBoardCount()
    }
}

</script>

<style scoped>

</style>