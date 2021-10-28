<template>
  <button type="button" class="btn btn-link">
    <button type="button" class="btn btn-link" @click="goBack">
      {{ lib }}
    </button>
  </button>
</template>

<script>
export default {
  name: "LinkButton",
  props: {
    lib: {
      type: String,
    },
    page: {
      type: String,
    },
  },
  methods: {
    goBack() {
      this.$router.go();
    },
  },
};
</script>

<style scoped>
.btn-link {
    color: black;
}
</style>