<template>
  <div class="container mt-5">
    <BoardMain v-bind:board-id="this.$route.params.id"></BoardMain>
    <Files v-bind:board-id="this.$route.params.id"></Files>
    <Comments v-bind:board-id="this.$route.params.id"></Comments>
    <Buttons></Buttons>
  </div>
</template>

<script>
import BoardMain from "@/components/board/BoardMain";
import Comments from "@/components/board/Comments";
import Files from "@/components/board/Files";
import Buttons from "@/components/board/Buttons";

export default {
  name: "BoardView",
  components: {
    BoardMain,
    Comments,
    Files,
    Buttons
  }
}
</script>

<style scoped>

</style>