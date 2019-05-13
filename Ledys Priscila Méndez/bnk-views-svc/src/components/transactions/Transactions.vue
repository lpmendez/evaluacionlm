<template>
    <div>
        <table class="blueTable">
            <thead v-if="transactions.length">
                <tr>
                    <th>ID</th>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Amount</th>
                </tr>
            </thead>
            <tbody v-if="transactions.length">
                <tr v-for="t in transactions" :key="t.id">
                    <td> {{ t.id }} </td>
                    <td> {{ t.date }} </td>
                    <td> {{ t.description }} </td>
                    <td> {{ t.amount }} </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    props: ['transactions']
}
</script>

<style>
table.blueTable {
  border: 1px solid #819EA4;
  background-color: #EEEEEE;
  width: 100%;
  text-align: left;
  border-collapse: collapse;
}
table.blueTable td, table.blueTable th {
  border: 1px solid #646464;
  padding: 3px 2px;
}
table.blueTable tbody td {
  font-size: 13px;
}
table.blueTable thead {
  background: #7796A4;
}
table.blueTable thead th {
  font-size: 15px;
  font-weight: bold;
  color: #FFFFFF;
}
table.blueTable tfoot td {
  font-size: 14px;
}
table.blueTable tfoot .links {
  text-align: right;
}
table.blueTable tfoot .links a{
  display: inline-block;
  background: #1C6EA4;
  color: #FFFFFF;
  padding: 2px 8px;
  border-radius: 5px;
}
</style>
