import React, {Component} from 'react';
import {Button, StyleSheet, View, Text, TextInput} from 'react-native';

export default class CinemaDetailComponent extends Component<{}>{

    constructor(props){
        super(props);

        this.state = {
            name: this.props.cinema.name,
            adress: this.props.cinema.adress,
            phoneNumber: this.props.cinema.phoneNumber
        };
    }

    render(){
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
