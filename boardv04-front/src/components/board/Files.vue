<template>
  <div class="row mb-5">
    <p v-for="file in files">
      <i class="fas fa-download"></i>
      <a v-on:click="downloadFile(file.fileId, file.originName)" v-text="file.originName" ></a>
    </p>
  </div>
</template>

<script>

export default {
  name: "Files",
  props: ['boardId'],
  data() {
    return {
      files: Array,
    }
  },
  methods: {
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
      this.$axios.get(`http://localhost:9090/files/${fileId}`,{responseType: "blob"})
          .then(response => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download',fileName);
            document.body.appendChild(link);
            link.click();
            console.log(response)
          })
          .catch(error => {
            console.log(error)
          });
    }
  },
  created() {
    this.getFiles()
  }
}
</script>

<style scoped>

</style>