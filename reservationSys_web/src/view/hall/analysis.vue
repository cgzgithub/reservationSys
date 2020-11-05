<template>
    <div class="container">
        <div class="title">
            展厅预约统计分析
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
                    <div class="text">选择展厅</div> 
                    <div class="room-item" :title="item.name" :class="{'room-select':item.name == searchRoom.name}" v-for="(item,index) in hallList" :key="index" @click="hallMapDataChange(item)">
                            {{item.name}}
                    </div>
                </div>
                <div id="map" class="map" style="height:400px">
                </div>
            </div>
            <div id="guidePersonTimeList" class="guidePersonTimeList block">
            </div>
        </div>
        <div id="everyday" class="everyday block" style="">
        </div>
    </div>
</template>
<script>
import api from '@/api/hall'
import {createUUID} from '@/utils/index'
export default {
    data(){
        return{
            dateRange:[new Date(this.func_date(new Date(),-60)).toISOString(),new Date().toISOString()],
            everyX:[],
            everySeries:[],
            guideAxis:[],
            guideSeries:[],
            mapSeries:[],
            hallList:[],
            guideList:[],
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
        // 切换展厅改变数据
        hallMapDataChange(item){
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
            this.guideSeries = []
            let params = {
                beginDate:dateRange[0],
                endDate:dateRange[1]
            }
            const that = this
            // api.getRoomList().then(res => {
                // let arr = []
                // res.data.list.forEach(function(item){
                //     arr.push(item.name)
                // });
                // that.hallList = res.data.list;
            // })
            // api.getGuideList().then(res => {
            //     let arr = []
            //     res.data.forEach(function(item){
            //         arr.push(item.name)
            //     });
            //     that.guideList = arr;
            // })
            api.analysis(params).then(res => {
                if(Object.keys(res.data).length == 0){
                    this.$Message.warning('该时间范围内暂无数据')
                    that.everyX = []
                    that.everySeries=[];
                    that.guideSeries=[];
                    that.mapSeries=[];
                    that.draw()
                    return
                }
                this.everyX = res.data.everydayPersonTimeList[0].xaxis.concat();
                this.everyX = this.everyX.map(function(value){
                    return value.substring(0,10)
                })
                that.hallList = res.data.showroomList
                res.data.everydayPersonTimeList.forEach(function(item){
                    item.series.type='line';
                    item.series.stack='接待总人数'
                    that.everySeries.push(item.series)
                })
                res.data.guidePersonTimeList.forEach(function(item){
                    let obj = {}
                    obj.name = item.name;
                    obj.value = item.personTime
                    that.guideAxis.push(item.name)
                    that.guideSeries.push(item.personTime)
                });
                for(let key in res.data.map){
                    let obj = {}
                    obj.name = key;
                    res.data.map[key].forEach(function(item){
                        item.name = item.status;
                        item.value = item.number;
                        delete item.showroomName
                        delete item.status
                        delete item.number
                    })
                    obj.seriesData = res.data.map[key]
                    that.mapList.push(obj);
                }
                that.mapSeries = that.mapList[0].seriesData
                that.$set(that.searchRoom,'name',that.mapList[0].name)
                that.draw()
            })
        },
        draw(){
            let myChart3 = this.$echarts.init(document.getElementById('everyday'));
            let myChart2 = this.$echarts.init(document.getElementById('guidePersonTimeList'));
            let myChart1 = this.$echarts.init(document.getElementById('map'));
            let option3 = 
                {
                    title: {
                        text: '展厅接待人次统计'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: this.hallList
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
            let option = {
                title: {
                        text: '讲解员接待人次比例'
                },
                color: ['#3398DB'],
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                        type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
                        type: 'category',
                        data: this.guideAxis,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '接待人次',
                        type: 'bar',
                        barWidth: '60%',
                        data: this.guideSeries
                    }
                ]
            };
            let option2 = 
            {
                title:{
                    text:'讲解员接待人次比例',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b}: {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'right',
                    top:30,
                    data: this.guideList
                },
                series: [
                    {
                        name: '接待人次',
                        type: 'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: this.guideSeries
                    }
                ]
            };

            let option1 = 
            {
                title: {
                    text: '展厅接待次数与取消次数比例图',
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
                    data: ['取消', '未到访','预约中','到访']
                },
                series: [
                    {
                        name: '',
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
            myChart2.setOption(option)
            myChart1.setOption(option1)
            window.addEventListener("resize", function() {
                myChart1.resize();
                myChart2.resize();
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
                 let fileName = '展厅预定情况数据统计'+createUUID().substring(0,4);
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
            margin-right: 30px;
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
                    white-space: nowrap;
                    overflow: hidden;
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