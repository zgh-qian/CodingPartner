import USER_ROLE from '@/constant/userRole'
import { RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  // 首页
  {
    path: '/',
    name: '首页',
    component: () => import('@/layouts/HomePageLayout.vue'),
    meta: {
      access: USER_ROLE.NOT_LOGIN
    },
    children: [
      {
        path: '/',
        name: '首页',
        component: () => import('@/views/home/HomeView.vue'),
        meta: {
          showInMenu: true,
          access: USER_ROLE.NOT_LOGIN
        }
      },
      {
        path: '/questionList',
        name: '题库',
        component: () => import('@/views/home/QuestionListView.vue'),
        meta: {
          showInMenu: true,
          access: USER_ROLE.NOT_LOGIN
        }
      },
      {
        path: '/competition',
        name: '比赛',
        component: () => import('@/views/home/CompetitionView.vue'),
        meta: {
          showInMenu: true,
          access: USER_ROLE.NOT_LOGIN
        }
      }
    ]
  },
  // 题目界面
  {
    path: '/question/:id',
    name: '题目页面',
    props: true, // 将路由参数映射到组件的 props
    component: () => import('@/layouts/QuestionLayout.vue'),
    meta: {
      access: USER_ROLE.NOT_LOGIN
    },
    children: [
      {
        path: '/question/:id',
        name: '题目展示页面',
        props: true, // 将路由参数映射到组件的 props
        component: () => import('@/views/question/views/QuestionView.vue'),
        meta: {
          access: USER_ROLE.NOT_LOGIN
        }
      },
      {
        path: '/question/:id/comment',
        name: '题目评论页面',
        props: true, // 将路由参数映射到组件的 props
        component: () => import('@/views/question/views/QuestionComment.vue'),
        meta: {
          access: USER_ROLE.NOT_LOGIN
        }
      },
      {
        path: '/question/:id/answer',
        name: '题目答案页面',
        props: true, // 将路由参数映射到组件的 props
        component: () => import('@/views/question/views/QuestionAnswer.vue'),
        meta: {
          access: USER_ROLE.NOT_LOGIN
        }
      },
      {
        path: '/question/:id/submissions',
        name: '题目提交列表页面',
        props: true, // 将路由参数映射到组件的 props
        component: () =>
          import('@/views/question/views/QuestionSubmissions.vue'),
        meta: {
          access: USER_ROLE.NOT_LOGIN
        }
      },
      {
        path: '/question/:id/submissions/:questionSubmitId',
        name: '单个题目提交页面',
        props: true, // 将路由参数映射到组件的 props
        component: () => import('@/views/question/views/QuestionSubmit.vue'),
        meta: {
          access: USER_ROLE.NOT_LOGIN
        }
      }
    ]
  },
  // 用户信息界面
  {
    path: '/user/info',
    name: '用户',
    component: () => import('@/layouts/UserDashboardLayout.vue'),
    meta: {
      access: USER_ROLE.USER
    },
    children: [
      {
        path: '/user/info',
        name: '查看用户信息',
        component: () => import('@/views/user/UserInfoView.vue'),
        meta: {
          access: USER_ROLE.USER
        }
      },
      {
        path: '/user/edit',
        name: '编辑用户信息',
        component: () => import('@/views/user/UserInfoEdit.vue'),
        meta: {
          access: USER_ROLE.USER
        }
      }
    ]
  },
  // 登录注册页面
  {
    path: '/auth',
    name: '权限界面',
    component: () => import('@/layouts/AuthLayout.vue'),
    meta: {
      access: USER_ROLE.NOT_LOGIN
    },
    children: [
      {
        path: '/auth/login',
        name: '登录',
        component: () => import('@/views/auth/LoginView.vue'),
        meta: {
          access: USER_ROLE.NOT_LOGIN
        }
      },
      {
        path: '/auth/register',
        name: '注册',
        component: () => import('@/views/auth/RegisterView.vue'),
        meta: {
          access: USER_ROLE.NOT_LOGIN
        }
      }
    ]
  },
  // 管理员界面
  {
    path: '/admin',
    name: '管理员首页',
    component: () => import('@/layouts/AdminDashboardLayout.vue'),
    meta: {
      access: USER_ROLE.ADMIN
    },
    children: [
      {
        path: '/admin',
        name: '管理员首页',
        component: () => import('@/views/admin/HomeView.vue'),
        meta: {
          showInMenu: true,
          access: USER_ROLE.ADMIN
        }
      },
      {
        path: '/admin/question',
        name: '题目列表',
        component: () => import('@/views/admin/QuestionDetailList.vue'),
        meta: {
          showInMenu: true,
          access: USER_ROLE.ADMIN
        }
      },
      {
        path: '/admin/submit',
        name: '提交列表',
        component: () => import('@/views/admin/QuestionSubmitView.vue'),
        meta: {
          showInMenu: true,
          access: USER_ROLE.ADMIN
        }
      },
      {
        path: '/admin/user',
        name: '用户列表',
        component: () => import('@/views/admin/UserListView.vue'),
        meta: {
          showInMenu: true,
          access: USER_ROLE.ADMIN
        }
      }
    ]
  },
  // 出错界面
  {
    path: '/404',
    name: '出错',
    component: () => import('@/layouts/ErrorPageLayout.vue'),
    meta: {
      access: USER_ROLE.NOT_LOGIN
    }
  }
]

export default routes
