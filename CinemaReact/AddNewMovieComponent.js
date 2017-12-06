import React, { Component } from 'react';
import {
    Button,
    Platform, ScrollView,
    StyleSheet,
    Text,
    View,
    Alert, TouchableHighlight, FlatList, TextInput, Linking
} from 'react-native';
import DatePicker from 'react-native-datepicker';

export default class AddNewMovieComponent extends Component<{}>{

    constructor(props){
        super(props);
        this.state = {
            title: "",
            date: "",
            duration: "",
            description: ""
        };
        this.handleAdd = this.handleAdd.bind(this);
        this.sendEmail = this.sendEmail.bind(this);
    }

    handleAdd(){
        this.props.onAdd({
            title: this.state.title,
            date: Number(this.state.date),
            duration: Number(this.state.duration),
            description: this.state.description
        })
    }

    sendEmail(){
        subject = "New movie";
        body = "Try to add new movie: \nTitle: " + this.state.title +
            "\nDate: " + this.state.date +
            "\nDuration: " + this.state.duration +
            "\nDescription: " + this.state.description;
        Linking.openURL('mailto:marius.comiati97@gmail.com?subject=' + subject + '&body=' + body);
    }

    render(){
        return (
            <View style = {styles.mainView}>
                <View>
                    <Text>Title: </Text>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1, borderBottomWidth: 0}}
                        onChangeText={(text) => this.setState({title: text})}
                        value={this.state.title}
                    />
                </View>
                <View>
                    <Text>Date: </Text>
                    <DatePicker
                        style={{width: 200}}
                        date={this.state.date}
                        mode="date"
                        placeholder="select date"
                        format="YYYY-MM-DD"
                        confirmBtnText="Confirm"
                        cancelBtnText="Cancel"
                        customStyles={{
                            dateIcon: {
                                position: 'absolute',
                                left: 0,
                                top: 4,
                                marginLeft: 0
                            },
                            dateInput: {
                                marginLeft: 36
                            }
                        }}
                        onDateChange={(date) => {
                            this.setState({date: date})
                        }}
                    />
                </View>
                <View>
                    <Text>Duration: </Text>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1, borderBottomWidth: 0}}
                        onChangeText={(text) => this.setState({duration: text})}
                        value={this.state.duration}
                        keyboardType={"numeric"}
                        maxLength={3}
                    />
                </View>
                <View>
                    <Text>Description: </Text>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1, borderBottomWidth: 0}}
                        onChangeText={(text) => this.setState({description: text})}
                        value={this.state.description}
                        multiline={true}
                    />
                </View>
                <Button
                    title={"Add"}
                    onPress={() => this.handleAdd()}
                />
                <Button
                    title={"Send email"}
                    onPress={() => {this.sendEmail()}}
                />
                <Button
                    title={"Back"}
                    onPress={() => this.props.onComeBack()}
                    style={{padding: 10}}
                />
            </View>
        );
    }
}
const styles = StyleSheet.create({
    mainView: {
        flex: 1
    },
    listView: {
        padding: 10,
        flex: 0.6,
        backgroundColor: 'white',
    },
    listItemView: {
        padding: 10
    },
    bigBlack: {
        fontSize: 20,
        fontWeight: 'bold',
    },
});