import React, { Component } from 'react';
import {
    Button,
    Platform, ScrollView,
    StyleSheet,
    Text,
    View,
    Alert, TouchableHighlight, FlatList, TextInput
} from 'react-native';
import DatePicker from 'react-native-datepicker';

export default class MovieDetailComponent extends Component<{}>{

    constructor(props){
        super(props);

        this.state = {
            title: this.props.movie.title,
            date: this.props.movie.date + "",
            duration: this.props.movie.duration + ""
        };

        this.handleUpdate = this.handleUpdate.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
    }

    handleUpdate(){
        this.props.onUpdate({
            key: this.props.movie.key,
            title: this.state.title,
            date: this.state.date,
            duration: this.state.duration,
            description: this.props.movie.description
        })
    }

    handleDelete(){
        Alert.alert(
            'Movie removal',
            'Are you sure you want to remove this movie?',
            [
                {text: 'Revoke', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
                {text: 'OK', onPress: () => this.props.onDelete(this.props.movie.key)},
            ],
            { cancelable: false }
        )
    }

    render(){
        return (
            <View style = {styles.mainView}>
                <View style={{flex: 7}}>
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
                    <Text>
                        {this.props.movie.description}
                    </Text>
                </View>
                <View style = {{flex: 3}}>
                    <Button
                        title={"Delete Me!"}
                        onPress={() => this.handleDelete()}
                        style={{padding: 10}}
                    />
                    <Button
                        title={"Update"}
                        onPress={() => this.handleUpdate()}
                    />
                    <Button
                        title={"Back"}
                        onPress={() => this.props.onComeBack()}
                        style={{padding: 10}}
                    />
                </View>
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
