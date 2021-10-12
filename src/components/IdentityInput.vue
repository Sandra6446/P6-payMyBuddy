<template>
  <div class="row justify-content-center my-3">
    <div class="col-10 col-md-8">
      <div class="input-group input-group-lg">
        <span class="input-group-text bg-transparent">
          <img
            src="../assets/person-fill.svg"
            class="img-person"
            alt="person image"
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
    </div>
  </div>
</template>

<script>
export default {
  name: "IdentityInput",
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