<template>
  <div class="row text-center">
    <div class="col">
      <button v-on:click="getListView" class="btn btn-outline-primary btn-lg" type="button">
        목록
      </button>
    </div>
    <div class="col">
      <button v-on:click="getPasswordCheckView('modify')" class="btn btn-outline-dark btn-lg" type="button">
        수정
      </button>
    </div>
    <div class="col">
      <button v-on:click="getPasswordCheckView('delete')" class="btn btn-outline-danger btn-lg" type="button">
        삭제
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Buttons",
  methods: {
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
    getPasswordCheckView(type) {
      this.$router.push({
        path: `/passwordCheck`,
        query: {
          curPage: this.$route.query.curPage,
          createdDateFrom: this.$route.query.createdDateFrom,
          createdDateTo: this.$route.query.createdDateTo,
          categoryId: this.$route.query.categoryId,
          text: this.$route.query.text,
          type: type
        }});
    }
  }
}
</script>

<style scoped>

</style>