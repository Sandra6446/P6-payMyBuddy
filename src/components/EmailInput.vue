<template>
  <div class="row justify-content-center my-3">
    <div class="col-10 col-md-8">
      <div class="input-group input-group-lg">
        <span class="input-group-text bg-transparent" id="email-addon">
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
    </div>
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