import React, {Component} from 'react';
import {Button, StyleSheet, View, Text, TextInput} from 'react-native';
import {Bar} from 'react-native-pathjs-charts';

export default class CinemaDetailComponent extends Component<{}>{

    constructor(props){
        super(props);

        this.computeChartInput = this.computeChartInput.bind(this);
        this.loadChartOptions = this.loadChartOptions.bind(this);

        this.loadChartOptions();
        const chartData = this.computeChartInput(this.props.chartData);

        this.state = {
            name: this.props.cinema.name,
            adress: this.props.cinema.adress,
            phoneNumber: this.props.cinema.phoneNumber,
            chartData: chartData
        };

        this.testChartData = this.testChartData.bind(this);
    }

    computeChartInput(assoc){
        let a = [];
        while (assoc.length > 0){
            searchedDate = assoc[0].date;
            nb = assoc.filter(x => x.date == searchedDate).length;
            a.push([{"name": searchedDate, "v":nb}]);
            assoc = assoc.filter(x => x.date != searchedDate);
        }
        return a;
    }

    loadChartOptions(){
        this.options = {
            width: 300,
            height: 300,
            margin: {
                top: 20,
                left: 25,
                bottom: 50,
                right: 20
            },
            color: '#2980B9',
            gutter: 20,
            animate: {
                type: 'oneByOne',
                duration: 200,
                fillTransition: 3
            },
            axisX: {
                showAxis: true,
                showLines: true,
                showLabels: true,
                showTicks: true,
                zeroAxis: false,
                orient: 'bottom',
                label: {
                    fontFamily: 'Arial',
                    fontSize: 8,
                    fontWeight: true,
                    fill: '#34495E'
                }
            },
            axisY: {
                showAxis: true,
                showLines: true,
                showLabels: true,
                showTicks: true,
                zeroAxis: false,
                orient: 'left',
                label: {
                    fontFamily: 'Arial',
                    fontSize: 8,
                    fontWeight: true,
                    fill: '#34495E'
                }
            }
        }
    }

    render(){
        mock_obj = this.testChartData();
        return (
            <View style = {styles.mainView}>
                <View>
                    <Text>Name: </Text>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1, borderBottomWidth: 0}}
                        onChangeText={(text) => this.setState({name: text})}
                        value={this.state.name}
                    />
                </View>
                <View>
                    <Text>Adress: </Text>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1, borderBottomWidth: 0}}
                        onChangeText={(text) => this.setState({adress: text})}
                        value={this.state.adress}
                    />
                </View>
                <View>
                    <Text>Phone number: </Text>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1, borderBottomWidth: 0}}
                        onChangeText={(text) => this.setState({phoneNumber: text})}
                        value={this.state.phoneNumber}
                        keyboardType={"numeric"}
                    />
                </View>
                <View style = {{flex: 1}}>
                    <Button
                        title={"Back"}
                        onPress={() => this.props.onComeBack()}
                        style={{padding: 10}}
                    />
                </View>
                <View>
                    <Bar data={this.state.chartData} options={this.options} accessorKey='v'/>
                </View>
            </View>
        );
    }

    testChartData(){
        let data = [
            [{
                "v": 49,
                "name": "apple"
            }, {
                "v": 42,
                "name": "apple"
            }],
            [{
                "v": 69,
                "name": "banana"
            }, {
                "v": 62,
                "name": "banana"
            }],
            [{
                "v": 29,
                "name": "grape"
            }, {
                "v": 15,
                "name": "grape"
            }]
        ];

        return {data};
    }
}
const styles = StyleSheet.create({
    mainView: {
        flex: 1
    },
    listView: {
        padding: 10,
        flex: 1,
        backgroundColor: 'red',
    },
    listItemView: {
        padding: 10,
        backgroundColor: 'pink'
    },
    bigBlack: {
        fontSize: 20,
        fontWeight: 'bold',
    },
});
