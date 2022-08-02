<template>
  <div class="container mt-5">
    <WriteBoard></WriteBoard>
  </div>
</template>

<script>
import WriteBoard from "@/components/write/WriteBoard";

export default {
  name: "WriteView",
  data() {
    return {
      categoryId: 0
    }
  },
  components: {
    WriteBoard
  }
}
</script>

<style scoped>

</style>