import React, { Component } from 'react';
import {
    Button,
    Platform, ScrollView,
    StyleSheet,
    Text,
    View,
    Alert, TouchableHighlight, FlatList, TextInput
} from 'react-native';

export default class MovieDetailComponent extends Component<{}>{

    constructor(props){
        super(props);

        this.state = {
            title: this.props.movie.title,
            year: this.props.movie.year + "",
            duration: this.props.movie.duration + ""
        };

        this.handleUpdate = this.handleUpdate.bind(this);
    }

    handleUpdate(){
        this.props.onUpdate({
            id: this.props.movie.id,
            title: this.state.title,
            year: this.state.year,
            duration: this.state.duration,
            description: this.props.movie.description
        })
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
                    <Text>Year: </Text>
                    <TextInput
                        style={{height: 40, borderColor: 'gray', borderWidth: 1, borderBottomWidth: 0}}
                        onChangeText={(text) => this.setState({year: text})}
                        value={this.state.year}
                        keyboardType={"numeric"}
                        maxLength={4}
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