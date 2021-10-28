<template>
  <button type="submit" class="btn btn-primary col-12">{{ lib }}</button>
</template>

<script>
export default {
  name: "SubmitButton",
  props: {
    lib: {
      type: String,
    },
  },
};
</script>