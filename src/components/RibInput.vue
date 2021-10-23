<template>
  <div class="input-group input-group-lg mb-3">
    <span class="input-group-text bg-white">
      <img
        src="../assets/vector-pen.svg"
        class="img vector-pen"
        alt="vector-pen image"
      />
    </span>
    <input
      class="form-control border-start-0"
      @input="emitCustomEvent"
      v-bind:value="value"
      type="text"
      v-bind:id="id"
      v-bind:placeholder="placeholder"
      v-bind:aria-label="placeholder"
      required
    />
  </div>
</template>

<script>
export default {
  name: "RibInput",
  props: {
    value: {
      type: String,
    },
    id: {
      type: String,
      required: true,
    },
    placeholder: {
      type: String,
      required: true,
    },
  },
  methods: {
    emitCustomEvent() {
      this.$emit("input", event.target.value);
    },
  },
};
</script>