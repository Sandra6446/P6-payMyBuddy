<template>
  <button type="submit" class="btn btn-primary btn-block">{{ lib }}</button>
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