<template>
  <div class="input-group input-group-lg mb-3">
    <span class="input-group-text bg-white" id="email-addon">
      <img
        src="../assets/envelope.svg"
        class="img-envelope"
        alt="envelope image"
      />
    </span>
    <input
      class="form-control border-start-0"
      @input="emitCustomEvent"
      v-bind:value="value"
      type="email"
      id="email"
      placeholder="Email"
      aria-label="Email"
      required
    />
  </div>
</template>

<script>
export default {
  name: "EmailInput",
  props: {
    value: {
      type: String,
    },
  },
  methods: {
    emitCustomEvent() {
      this.$emit("input", event.target.value);
    },
  },
};
</script>

<style>
</style>