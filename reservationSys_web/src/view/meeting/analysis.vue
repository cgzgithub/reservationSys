<template>
    <div class="container">
        <div class="title">
            会议室预约统计分析
            <el-button size="small" type="primary" @click="exportFile" style="margin-left:20px">导出数据</el-button>
        </div>
        <div class="date">
            <el-date-picker
                style="width:360px"
                @change="dateChange"
                v-model="dateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
            </el-date-picker>
        </div>
        <div class="chart-map">
            <div class="map-box block">
                <div class="room">
                    <div class="text">选择会议室</div> 
                    <div :title="item.name" class="room-item" :class="{'room-select':item.name == searchRoom.name}" v-for="(item,index) in meetingList" :key="index" @click="meetingMapDataChange(item)">
                            {{item.name}}
                    </div>
                </div>
                <div id="map" class="map" style="height:400px">
                </div>
            </div>
            
            <!-- <div id="guidePersonTimeList" class="guidePersonTimeList block">
            </div> -->
        </div>
        <div id="everyday" class="everyday block" style="">
        </div>
    </div>
</template>
<script>
import api from '@/api/meeting'
import dict from '@/api/dict'
import {createUUID} from '@/utils/index'
export default {
    data(){
        return{
            dateRange:[new Date(this.func_date(new Date(),-60)).toISOString(),new Date().toISOString()],
            everyX:[],
            everySeries:[],
            comTypeSeries:[],
            mapSeries:[],
            meetingList:[],
            companyTypeList:[],
            mapList:[],
            searchRoom:{}
        }
    },
    created(){
        this.getData(this.dateRange)
    },
    mounted(){
        this.draw()
    },
    methods:{
        dateChange(v){
            let ranges = JSON.parse(JSON.stringify(this.dateRange));
            let dateStart = Date.parse(ranges[0]);
            let dateEnd = Date.parse(ranges[1]);
            let days = (dateEnd - dateStart)/(1*24*60*60*1000);
            if(days > 365){
                this.$Message.error('日期范围不能大于一年')
            }else{
                this.getData(ranges)
            }
            
        },
        // 日期加减函数
        func_date(curdate,days){
            var date1 = new Date(curdate),
            time1=date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();//time1表示当前时间
            var date2 = new Date(date1);
            date2.setDate(date1.getDate()+days);
            var time2 = date2.getFullYear()+"-"+(date2.getMonth()+1)+"-"+date2.getDate();
            return JSON.parse(JSON.stringify(date2));
        },
        // 切换会议室改变数据
        meetingMapDataChange(item){
            this.searchRoom = item
            let data  = this.mapList.find(function(item1){
                return item1.name == item.name;
            })
            if(data){
                this.mapSeries = data.seriesData;
            }else{
                this.mapSeries = [];
            }
            this.draw()
        },
        getData(dateRange){
            this.everySeries = []
            this.comTypeSeries = []
            let params = {
                beginDate:dateRange[0],
                endDate:dateRange[1]
            }
            const that = this
            dict.getDictInfoByType({type:'companyType'}).then(res => {
                let arr = []
                res.data.forEach(function(item){
                    arr.push(item.title)
                });
                that.companyTypeList = arr;
            })
            api.analysis(params).then(res => {
                if(Object.keys(res.data).length == 0){
                    this.$Message.warning('该时间范围内暂无数据')
                    that.everyX = []
                    that.everySeries=[];
                    that.comTypeSeries=[];
                    that.mapSeries=[];
                    that.draw()
                    return
                }
                this.everyX = res.data.everydayPersonTimeList[0].xaxis.concat();
                this.everyX = this.everyX.map(function(value){
                    return value.substring(0,10)
                })
                this.meetingList = res.data.meetingList
                res.data.everydayPersonTimeList.forEach(function(item){
                    item.series.type='line';
                    item.series.stack='接待总人数'
                    that.everySeries.push(item.series)
                })
                // res.data.companyTypeAndTimesList.forEach(function(item){
                //     let obj = {}
                //     obj.name = item.companyType;
                //     obj.value = item.times
                //     that.comTypeSeries.push(obj)
                // });
                that.mapList = res.data.meetingUseList.concat()
                that.mapList.forEach(function(item){
                    item.name = item.meetingName
                    let arr = []
                    for(let key in item.map){
                        let obj = {}
                        obj.name = key;
                        obj.value = item.map[key]
                        arr.push(obj)
                    }
                    item.seriesData = arr;
                    delete item.meetingName
                    delete item.map;
                })
                
                that.mapSeries = that.mapList[0].seriesData
                that.$set(that.searchRoom,'name',that.mapList[0].name)
                that.draw()
            })
        },
        draw(){
            let myChart3 = this.$echarts.init(document.getElementById('everyday'));
            // let myChart2 = this.$echarts.init(document.getElementById('guidePersonTimeList'));
            let myChart1 = this.$echarts.init(document.getElementById('map'));
            let option3 = 
                {
                    title: {
                        text: '会议室接待人次统计'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: this.meetingList
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: this.everyX
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: this.everySeries
                };
            // let option2 = 
            // {
            //     title:{
            //         text:'参会企业类型比例',
            //         left: 'center'
            //     },
            //     tooltip: {
            //         trigger: 'item',
            //         formatter: '{a} <br/>{b}: {c} ({d}%)'
            //     },
            //     legend: {
            //         orient: 'vertical',
            //         left: 'right',
            //         top:30,
            //         data: this.companyTypeList
            //     },
            //     series: [
            //         {
            //             name: '类型总量',
            //             type: 'pie',
            //             radius: ['50%', '70%'],
            //             avoidLabelOverlap: false,
            //             label: {
            //                 show: false,
            //                 position: 'center'
            //             },
            //             emphasis: {
            //                 label: {
            //                     show: true,
            //                     fontSize: '30',
            //                     fontWeight: 'bold'
            //                 }
            //             },
            //             labelLine: {
            //                 show: false
            //             },
            //             data: this.comTypeSeries
            //         }
            //     ]
            // };

            let option1 = 
            {
                title: {
                    text: '各会议室使用率',
                    subtext: this.searchRoom.name,
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'right',
                    top:30,
                    data: ['使用时长（小时）', '空闲时长（小时）']
                },
                series: [
                    {
                        name: this.searchRoom.name,
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: this.mapSeries,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            myChart3.setOption(option3)
            // myChart2.setOption(option2)
            myChart1.setOption(option1)
            window.addEventListener("resize", function() {
                myChart1.resize();
                // myChart2.resize();
                myChart3.resize();
            })
        },
        exportFile(){
            let ranges = JSON.parse(JSON.stringify(this.dateRange))
            let params = {
                beginDate:ranges[0],
                endDate:ranges[1]
            }
             api.exportAnalysisExcel(params,{responseType:'blob'}).then(res => {
                 let url = window.URL.createObjectURL(new Blob([res]));
                 let link = document.createElement('a');
                 link.style.display = 'none';
                 link.href = url;
                 let fileName = '会议室预定情况数据统计'+createUUID().substring(0,8);
                 link.setAttribute('download',fileName+'.xls');
                 document.body.appendChild(link);
                 link.click();
                 document.body.removeChild(link)
             })
        }
    }
}
</script>
<style lang="scss" scoped>
div{
    box-sizing: border-box;
}
.container{
    padding:0 20px;
    .title{
        font-weight: bold;
        font-size: 16px;
        margin-bottom:20px;
    }
    .date{
        margin-bottom:20px;
    }
    
    .chart-map{
        display: flex;
        .map-box{
            flex: 1;
            // margin-right: 30px;
            display: flex;
            .room{
                min-width:15%;
                .text{
                    font-weight: bold;
                    margin-bottom: 5px;
                }
                .room-item{
                    cursor: pointer;
                    padding:5px 15px;
                    max-width: 100px;
                    text-overflow: ellipsis;
                    overflow: hidden;
                    white-space: nowrap;
                }
                .room-select{
                    background-color: #617FDE;
                    color: #fff;
                }
            }
            .map{
                flex:1
            }
        }
        .guidePersonTimeList{
            flex: 1;
        }
    }
    .everyday{
        margin-top:40px;
        width: auto;
        height: 400px;
    }
}
.block{
    padding:20px;
    background-color: #fff;
    box-shadow: 0px 0px 8px 4px rgb(228, 225, 225);
}


</style>