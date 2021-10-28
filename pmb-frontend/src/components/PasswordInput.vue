<template>
  <div v-if="passwordRequired" class="input-group input-group-lg mb-3">
    <span class="input-group-text bg-white">
      <img
        src="../assets/asterisk.svg"
        class="img-asterisk"
        alt="asterisk image"
      />
    </span>
    <input
      class="form-control border-start-0"
      @input="emitCustomEvent"
      v-bind:value="value"
      type="password"
      v-bind:id="id"
      v-bind:placeholder="placeholder"
      v-bind:aria-label="placeholder"
      required
    />
  </div>
  <div v-else class="input-group input-group-lg mb-3">
    <span class="input-group-text bg-white">
      <img
        src="../assets/asterisk.svg"
        class="img-asterisk"
        alt="asterisk image"
      />
    </span>
    <input
      class="form-control border-start-0"
      @input="emitCustomEvent"
      v-bind:value="value"
      type="password"
      v-bind:id="id"
      v-bind:placeholder="placeholder"
      v-bind:aria-label="placeholder"
    />
  </div>
</template>

<script>
export default {
  name: "PasswordInput",
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
    passwordRequired: {
      type:Boolean
    }
  },
  methods: {
    emitCustomEvent() {
      this.$emit("input", event.target.value);
    },
  },
};
</script>