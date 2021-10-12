<template>
    <div class="bordered-container" id="addConnection">

            <b-row class="justify-content-center my-5">
                        <MainTitle></MainTitle>
                    </b-row>

            <form @submit="onSubmit" method="post" action="/connection">

            <b-col sm="10" class="mx-auto">

                <EmailInput class="my-5" v-model="form.email"></EmailInput>

               <b-row class="my-5">
<b-col md="4"><LinkButton lib="Back" page="/"></LinkButton></b-col>
    <b-col md="4" offset-md="4"><SubmitButton lib="Add"></SubmitButton></b-col>


                           </b-row>

                           </b-col>
            </form>

    </div>
</template>

<script>
    import MainTitle from '../components/MainTitle.vue'
    import SubmitButton from '../components/SubmitButton.vue'
    import EmailInput from '../components/EmailInput.vue'
    import LinkButton from '../components/LinkButton.vue'

    export default {
        name: 'AddConnection',
        components: {
            MainTitle,
            SubmitButton,
            EmailInput,
            LinkButton
        },
        data () {
            return {
                form: {
                                    email: ''
                                }
              }
          },
                  methods: {
                      onSubmit(event) {
                          event.preventDefault()
                          alert(JSON.stringify(this.form))
                          window.open("/transfer","_self")
                      }
                  }
      }
  </script>
