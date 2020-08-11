var messageApi = Vue.resource('/rest/v1/messages{/id}');

function getIndex(list, id){
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

Vue.component('message-row', {
    props: ['message', 'editMethod', 'messages'],
    template: '<li><i>({{message.id}}) </i>{{message.name}}' +
            '<span style="position: absolute; right: 0">' +
                '<input type="button" value="Edit" @click="edit">' +
                '<input type="button" value="X" @click="del">' +
            '</span>' +
            '</li>',
    methods: {
        edit: function () {
            this.editMethod(this.message);
        },
        del: function () {
            messageApi.remove({id: this.message.id}).then(result => {
                    if (result.ok) {
                        this.messages.splice(this.messages.indexOf(this.message), 1)
                    }
                }
            )
        }
    }
})


Vue.component('message-form', {
    props: ['messages', 'messageName'],
    data: function () {
        return{
            name: '',
            id: ''
        }
    },
    watch:{
        messageName: function (newVal, oldVal) {
            this.name = newVal.name;
            this.id = newVal.id;
        }
    },
    template: '<div>' +
                    '<input type="text" placeholder="write something" v-model="name"/>' +
                    '<input type="button" value="Save" @click="save"/>' +
            '</div>',
    methods: {
        save: function () {
            var message = {name: this.name};

            if(this.id){
                messageApi.update({id: this.id}, message).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.messages, data.id);
                        this.messages.splice(index, 1, data);
                        this.name = '';
                        this.id = '';
                    }))
            } else {
                messageApi.save({}, message).then(result =>
                    result.json().then(data => {
                        this.messages.push(data);
                        this.name = ''
                    }))
            }

        }
    }
})


Vue.component('messages-list', {
    props: ['messages'],
    data: function(){
      return{
          message: null
      }
    },
    template: '<div style="position: relative; width: 400px">' +
            '<message-form :messages="messages" :messageName="message"/>' +
            '<message-row v-for="item in messages" :key="item.id" :message="item" :messages="messages" :editMethod="editMethod"/>' +
        '</div>',
    created: function () {
        messageApi.get().then(result =>
            result.json().then(data =>
                data.forEach(message => this.messages.push(message))
            )
        )
    },
    methods:{
        editMethod: function (message) {
            this.message = message;
        }
    }
})

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages"/>',
    data: {
        message: 'Hello!',
        messages: []
    }

});