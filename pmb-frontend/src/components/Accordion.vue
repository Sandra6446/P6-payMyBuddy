<template>
  <div class="accordion" id="accordion">
    <div class="accordion-item">
      <h2 class="accordion-header" id="headingLogin">
        <button
          class="accordion-button"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#collapseLogin"
          aria-expanded="true"
          aria-controls="collapseLogin"
        >
          Identity
        </button>
      </h2>
      <div
        id="collapseLogin"
        class="accordion-collapse collapse show"
        aria-labelledby="headingLogin"
      >
        <div class="accordion-body">
          <IdentityInput
            id="firstName"
            placeholder="Firstname"
            v-model="form.firstName"
          ></IdentityInput>

          <IdentityInput
            id="lastName"
            placeholder="Lastname"
            v-model="form.lastName"
          ></IdentityInput>
          <EmailInput v-model="form.email"></EmailInput>

          <PasswordInput
            id="password"
            placeholder="Password"
            v-model="form.password"
          ></PasswordInput>

          <PasswordInput
            id="confirmPassword"
            placeholder="Confirm password"
            v-model="form.confirmPassword"
          ></PasswordInput>
        </div>
      </div>
    </div>

    <div class="accordion-item">
      <h2 class="accordion-header" id="headingBank">
        <button
          class="accordion-button"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#collapseBank"
          aria-expanded="true"
          aria-controls="collapseBank"
        >
          Bank
        </button>
      </h2>
      <div
        id="collapseBank"
        class="accordion-collapse collapse show"
        aria-labelledby="headingBank"
      >
        <div class="accordion-body">
          <RibInput
            class="my-3"
            id="bank"
            placeholder="Bank"
            v-model="form.bankAccount.bank"
          ></RibInput>
          <RibInput
            class="my-3"
            id="iban"
            placeholder="IBAN"
            v-model="form.bankAccount.iban"
          ></RibInput>
          <RibInput
            class="my-3"
            id="bic"
            placeholder="BIC"
            v-model="form.bankAccount.bic"
          ></RibInput>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Accordion",
};
</script>