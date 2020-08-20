<template>
    <v-row>
    <v-col cols="12" sm="6" md="3">
         <v-text-field
            label="New message"
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

    import serverApi from 'api/messages'

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

                    const message = {
                        id: this.id,
                        name: this.name}

                    if(this.id){
                        serverApi.update(message).then(result =>
                            result.json().then(data => {
                                const index = this.messages.findIndex(item => item.id === data.id)
                                this.messages.splice(index, 1, data)
                            }))
                    } else {
                        serverApi.add(message).then(result =>
                            result.json().then(data => {
                                    const index = this.messages.findIndex(item => item.id === data.id)

                                    if(index > -1){
                                        this.messages.splice(index, 1, data)
                                    } else{
                                    this.messages.push(data)
                                    }
                            }))
                    }

                    this.name = ''
                    this.id = ''
                }
            }
    }
</script>

<style>

</style>