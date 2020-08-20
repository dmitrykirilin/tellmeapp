<template>
    <v-layout align-space-around justify-start column fluid>
        <message-form :messages="messages" :messageName="message"/>
        <message-row v-for="item in sortedMessages"
                    :key="item.id"
                    :message="item"
                    :messages="messages"
                    :editMessage="editMessage"
                    :deleteMessage="deleteMessage"/>
    </v-layout>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import serverApi from 'api/messages'

    export default {
        props: ['messages'],
        components: {
            MessageRow,
            MessageForm
        },
        data() {
            return {
                message: null
            }
        },
        computed:{
            sortedMessages(){
                return this.messages.sort((a, b) => -(a.id - b.id))
            }
        },
        methods: {
            editMessage(message) {
                this.message = message;
            },
            deleteMessage(message){
                 serverApi.remove(message.id).then(result => {
                      if (result.ok) {
                           this.messages.splice(this.messages.indexOf(message), 1)
                           }
                      }
                 )
            }
        }
    }
</script>

<style>

</style>