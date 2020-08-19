<template>
<v-col cols="12">
 <v-card
    class="my-2">

      <v-list-item>
        <v-list-item-content>
          <v-card-text primary-title class="headline"><i>({{message.id}}) </i>{{message.name}}</v-card-text>
         </v-list-item-content>
      </v-list-item>

      <v-card-actions>
        <v-btn @click="edit" small>Edit</v-btn>
        <v-btn icon @click="del" small>
            <v-icon>mdi-delete</v-icon>
        </v-btn>
      </v-card-actions>

 </v-card>
</v-col>
</template>

<script>
    export default {
        props: ['message', 'editMessage', 'deleteMessage', 'messages'],
        methods: {
               edit() {
                   this.editMessage(this.message)
               },
               del() {
                    this.deleteMessage(this.message)
               }
           }
    }
</script>

<style>

</style>