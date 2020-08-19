<template>
    <v-row>
    <v-col cols="12" sm="6" md="3">
         <v-text-field label="New message"
            placeholder="write something"
            v-model="name"
            solo
         />
         </v-col>
         <v-col cols="12" sm="6" md="3">
         <v-btn @click="save">
            Save
         </v-btn>
         </v-col>
    </v-row>
</template>

<script>

    import { sendMessage } from "util/ws";

    export default {
        props: ['messages', 'messageName'],
        data() {
                return{
                    name: '',
                    id: ''
                }
            },
            watch:{
                messageName(newVal, oldVal) {
                    this.name = newVal.name
                    this.id = newVal.id
                }
            },
            methods: {
                save() {
                    sendMessage({id: this.id, name: this.name})
                    this.name = ''
                    this.id = ''
                    /*const message = {name: this.name}

                    if(this.id){
                        this.$resource('/rest/v1/messages{/id}').update({id: this.id}, message).then(result =>
                            result.json().then(data => {
                                const index = getIndex(this.messages, data.id)
                                this.messages.splice(index, 1, data)
                                this.name = ''
                                this.id = ''
                            }))
                    } else {
                        this.$resource('/rest/v1/messages{/id}').save({}, message).then(result =>
                            result.json().then(data => {
                                 this.messages.push(data)
                                this.name = ''
                            }))
                    }*/
                }
            }
    }
</script>

<style>

</style>