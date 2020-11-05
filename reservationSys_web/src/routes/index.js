import Vue from 'vue';
import Router from 'vue-router';
import store from '@/store';
Vue.use(Router);
const router = new Router({
    mode:"history",
    scrollBehavior(to,from,savedPosition) {
        if(savedPosition){
            return savedPosition
        }
        return {
            x: 0,
            y:0
        }
    },
    routes:[
        {
            path:'/',
            component: () => import('@/components/layout'),
            children:[{
                path:'/',
                component: () => import('@/view/home'),
                meta: {
                    title: '首页',
                },
            }

            ]
        },
        {
            path:'/base',
            name:'基础功能',
            component: () => import('@/components/layout'),
            meta: {
                title: '基础功能',
            },
            children:[
                {
                    path:'/base/account-list',
                    component: () => import('@/view/base/account-list'),
                    meta: {
                        title: '账号列表',
                    } 
                },
                {
                    path:'/base/role-list',
                    name:'角色列表',
                    component: () => import('@/view/base/role-list'),
                    meta: {
                        title: '角色列表',
                    }
                },
                {
                    path:'/base/dict',
                    name:'数据字典',
                    component: () => import('@/view/base/dict'),
                    meta: {
                        title: '数据字典',
                    }
                }
            ]
        },
        {
            path:'/hall',
            name:'展厅参观预约',
            component: () => import('@/components/layout'),
            meta: {
                title: '展厅参观预约',
            },
            children:[
                {
                    path:'/hall/hall-list',
                    component: () => import('@/view/hall/hall-list'),
                    meta: {
                        title: '展厅列表',
                    } 
                },
                {
                    path:'guide-list',
                    name:'讲解员列表',
                    component: () => import('@/view/hall/guide-list'),
                    meta: {
                        title: '讲解员列表',
                    }
                },
                {
                    path:'reservate',
                    name:'展厅参观预约',
                    component: () => import('@/view/hall/reservate'),
                    meta: {
                        title: '展厅参观预约',
                    }
                },
                {
                    path:'reservation-list',
                    name:'预约列表',
                    component: () => import('@/view/hall/reservation-list'),
                    meta: {
                        title: '预约列表',
                    }
                },
                {
                    path:'analysis',
                    name:'统计分析',
                    component: () => import('@/view/hall/analysis'),
                    meta: {
                        title: '统计分析',
                    }
                },
            ]
        },
        {
            path:'/meeting',
            name:'会议室预约',
            component: () => import('@/components/layout'),
            meta: {
                title: '会议室预约',
            },
            children:[
                {
                    path:'/meeting/roomlist',
                    component: () => import('@/view/meeting/roomlist'),
                    meta: {
                        title: '会议室列表',
                    } 
                },
                {
                    path:'/meeting/reservate',
                    component: () => import('@/view/meeting/reservate'),
                    meta: {
                        title: '会议室预约',
                    } 
                },
                {
                    path:'/meeting/reservate/perform-hall',
                    component: () => import('@/view/meeting/reservate-performhall'),
                    meta: {
                        title: '路演厅预约',
                    } 
                },
                {
                    path:'/meeting/reservation-list',
                    component: () => import('@/view/meeting/reservation-list'),
                    meta: {
                        title: '预约列表',
                    } 
                },
                {
                    path:'/meeting/analysis',
                    name:'统计分析',
                    component: () => import('@/view/meeting/analysis'),
                    meta: {
                        title: '统计分析',
                    }
                },
            ]
        },
        {
            path:'/apartment',
            name:'人才公寓',
            component: () => import('@/components/layout'),
            meta: {
                title: '人才公寓',
            },
            children:[
                {
                    path:'/apartment/apartment-list',
                    component: () => import('@/view/apartment/apartment-list'),
                    meta: {
                        title: '公寓列表',
                    }
                },
                {
                    path:'/apartment/company-list',
                    component: () => import('@/view/apartment/company-list'),
                    meta: {
                        title: '公司列表',
                    }
                },
                {
                    path:'/apartment/applylist',
                    component: () => import('@/view/apartment/applylist'),
                    meta: {
                        title: '申请列表',
                    }
                },
                {
                    path:'/apartment/reviewlist/self',
                    component: () => import('@/view/apartment/reviewlist-self'),
                    meta: {
                        title: '复核列表',
                    }
                },
                {
                    path:'/apartment/reviewlist/apartment',
                    component: () => import('@/view/apartment/reviewlist-apartment'),
                    meta: {
                        title: '湾区分管审核列表',
                    }
                },
                {
                    path:'/apartment/reviewlist/company',
                    component: () => import('@/view/apartment/reviewlist-company'),
                    meta: {
                        title: '湾区总经理审核列表',
                    }
                },
                {
                    path:'/apartment/reviewlist/leader',
                    component: () => import('@/view/apartment/reviewlist-leader'),
                    meta: {
                        title: '镇分管审核列表',
                    }
                },
            ]
        },
        {
            path:'/plan',
            name:'规划建设部',
            component: () => import('@/components/layout'),
            meta: {
                title: '规划建设部',
            },
            children:[
                {
                    path:'/plan/project-list',
                    component: () => import('@/view/plan/project-list'),
                    meta: {
                        title: '工程项目',
                    },
                    
                },
                {
                    path:'/plan/planing-adjustment-list',
                    component: () => import('@/view/plan/planAdjustment-list'),
                    meta: {
                        title: '规划调整',
                    },
                    
                },
                {
                    path:'/plan/land-transfer-list',
                    component: () => import('@/view/plan/landTransfer-list'),
                    meta: {
                        title: '土地前期出让',
                    },
                    
                },
                {
                    path:'/plan/early-construction-list',
                    component: () => import('@/view/plan/earlyConstruction-list'),
                    meta: {
                        title: '项目前期报建',
                    },
                    
                },
                {
                    path:'/plan/contract-manager',
                    component: () => import('@/view/plan/contract-manager'),
                    meta: {
                        title: '合同管理',
                    }
                },
                {
                    path:'/plan/file-manager',
                    component: () => import('@/view/plan/file-manager'),
                    meta: {
                        title: '资料归档',
                    }
                }
            ]
        },
        {
            path:'/login',
            name:'login',
            component: () => import('@/view/login'),
            meta: {
                title: '登录',
            }
        },
        {
            path:'/authUser',
            name:'authUser',
            component: () => import('@/view/authPage'),
            meta: {
                title: '登录验证',
            }
        }
    ]
});
export default router;