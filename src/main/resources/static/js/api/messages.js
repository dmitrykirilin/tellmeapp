import Vue from 'vue'

const serverApi = Vue.resource("/rest/v1/messages{/id}")

export default {
    add: message => serverApi.save({}, message),
    update: message => serverApi.update({id: message.id}, message),
    remove: id => serverApi.remove({id})
}