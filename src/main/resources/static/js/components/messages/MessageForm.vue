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

    import { mapActions } from 'vuex'

    export default {
        props: ['messageName'],

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
                ...mapActions(['addMessageAction', 'updateMessageAction']),
                save() {

                    const message = {
                        id: this.id,
                        name: this.name}

                    if(this.id){
                        this.updateMessageAction(message)
                    } else {
                        this.addMessageAction(message)
                    }

                    this.name = ''
                    this.id = ''
                }
            }
    }
</script>

<style>

</style>