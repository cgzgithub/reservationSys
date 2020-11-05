export const leftmenu = [
      {
        text: '首页',
        path: '/'
      },
      {
        text: '基础功能',
        path: '/base',
        subMenu: [
            {
                text:'账号列表',
                path:'/base/account-list'
            },
            {
                text:'角色列表',
                path:'/base/role-list'
            },
            {
                text:'数据字典',
                path:'/base/dict'
            }
        ]
      },
      {
        text: '展厅参观预约',
        path: '/hall',
        subMenu: [
            {
                text:'展厅列表',
                path:'/hall/hall-list'
            },
            {
                text:'讲解员列表',
                path:'/hall/guide-list'
            },
            {
                text:'展厅预约',
                path:'/hall/reservate'
            },
            {
                text:'展厅预约列表',
                path:'/hall/reservation-list'
            },
            {
                text:'统计分析',
                path:'/hall/analysis'
            },
        ]
      },
      {
        text: '会议室预约',
        path: '/meeting',
        subMenu: [
            {
                text:'会议室列表',
                path:'/meeting/roomlist'
            },
            {
                text:'会议室预约',
                path:'/meeting/reservate'
            },
            {
                text:'路演厅预约',
                path:'/meeting/reservate/perform-hall'
            },
            {
                text:'预约列表',
                path:'/meeting/reservation-list'
            },
            {
                text:'统计分析',
                path:'/meeting/analysis'
            },
        ]
      },
      {
        text: '人才公寓',
        path: '/apartment',
        subMenu:[
            {
                text:'公寓列表',
                path:'/apartment/apartment-list'
            },
            {
                text:'公司列表',
                path:'/apartment/company-list'
            },
            {
                text:'申请列表',
                path:'/apartment/applylist'
            },
            {
                text:'企业自审列表',
                path:'/apartment/reviewlist/self'
            },
            {
                text:'自审历史列表',
                path:'/apartment/reviewhi-list/self'
            },
            {
                text:'人才公寓审核列表',
                path:'/apartment/reviewlist/apartment'
            },
            {
                text:'人才公寓审核历史列表',
                path:'/apartment/reviewhi-list/apartment'
            },
            {
                text:'审核单位审核列表',
                path:'/apartment/reviewlist/company'
            },
            {
                text:'审核单位审核历史列表',
                path:'/apartment/reviewhi-list/company'
            },
            {
                text:'分管领导审核列表',
                path:'/apartment/reviewlist/leader'
            },
            {
                text:'分管领导审核历史列表',
                path:'/apartment/reviewhi-list/leader'
            }
        ]
      },
      {
        text: '规划建设部',
        path: '/plan',
        subMenu:[
            {
                text:'工程项目',
                path:'/plan/project-list'
            },
            {
                text:'规划调整',
                path:'/plan/planing-adjustment-list'
            },
            {
                text:'土地前期出让',
                path:'/plan/land-transfer-list'
            },
            {
                text:'项目前期报建',
                path:'/plan/early-construction-list'
            },
            {
                text:'合同管理',
                path:'/plan/contract-manager'
            },

        ]
      }
    ];
  