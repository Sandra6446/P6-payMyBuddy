<template>
  <b-container fluid id="transfer">
    <Header currentPage="Transfer"></Header>

    <div class="px-5">
      <div class="row align-items-center justify-content-between">
        <div class="col-auto text-center">Send Money</div>
        <div class="col-auto">
          <a class="btn btn-primary" href="/add_connection" role="button"
            >Add Connection</a
          >
        </div>
      </div>

      <form>
        <div class="card text-dark bg-light mb-3">
          <div class="card-body align-items-center">
            <div class="row justify-content-center">
              <div class="col-4">
                <select
                  class="form-select form-select-md"
                  aria-label="Select connection"
                >
                  <option selected>Connection</option>
                  <option value="1">Hayley</option>
                  <option value="2">Smith</option>
                  <option value="3">Clara</option>
                </select>
              </div>
              <div class="col-2">
                <input
                  type="number"
                  class="form-control"
                  value="0"
                  aria-label="Amount"
                />
              </div>
              <div class="d-grid col-2">
                <button class="btn btn-success" type="submit">Pay</button>
              </div>
            </div>
          </div>
        </div>
      </form>

      <b-table
        id="my_transactions"
        striped
        caption-top
        class="text-center"
        :items="items"
        :current-page="currentPage"
        :per-page="perPage"
      >
        <template #table-caption>My transactions</template>
      </b-table>

      <b-pagination
        align="center"
        v-model="currentPage"
        :total-rows="totalRows"
        :per-page="perPage"
        aria-controls="my_transactions"
      ></b-pagination>
    </div>
  </b-container>
</template>

 <script>
import Header from "../components/Header.vue";

export default {
  name: "Transfer",
  components: {
    Header,
  },
  data() {
    return {
      items: [
        {
          connection: "Hayley",
          description: "Restaurant bill share",
          amount: "10€",
        },
        { connection: "Hayley", description: "Trip money", amount: "25€" },
        { connection: "Smith", description: "Movie tickets", amount: "8€" },
      ],
      perPage: 3,
      totalRows: 1,
      currentPage: 1,
    };
  },
  mounted() {
    // Set the initial number of items
    this.totalRows = this.items.length;
  },
};
</script>

 <style scoped>
.card {
  height: 150px;
}

.input-symbol-euro {
  position: relative;
}
.input-symbol-euro input {
  padding-left: 18px;
}

.input-symbol-euro:before {
  position: absolute;
  top: 8px;
  content: "€";
  right: 40px;
}

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  opacity: 1;
}
</style>

