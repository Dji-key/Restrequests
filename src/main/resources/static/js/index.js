var app = new Vue({
    el: '#app',
    data: {
        page: 1,
        code: 0,
        users: []
    },
    created() {
        this.loadUsersPage(1);
    },
    methods: {
        loadUsersPage: function(page) {
            $.get('/users?page=' + page, function (response) {
                app.pageInfo = response;
                app.users = app.pageInfo.users;
            });
        },
        next: function () {
            this.page = this.page + 1;
            $.get('/users?page=' + this.page, function (data, textStatus, xhr) {
                app.pageInfo = data;
                app.users = app.pageInfo.users;
                app.code = xhr.status;
            });
        },
        prev: function () {
            this.page = this.page - 1;
            $.get('/users?page=' + this.page, function (data, textStatus, xhr) {
                app.pageInfo = data;
                app.users = app.pageInfo.users;
                app.code = xhr.status;
            });
        }
    }
});