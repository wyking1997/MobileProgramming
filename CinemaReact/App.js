/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
    Button,
    Platform, ScrollView,
    StyleSheet,
    Text,
    View,
    Alert, TouchableHighlight, FlatList
} from 'react-native';


const instructions = Platform.select({
    ios: 'Press Cmd+R to reload,\n' +
    'Cmd+D or shake for dev menu',
    android: 'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

export default class App extends Component<{}> {
    render() {
        return (
            <FlatList style={{flex: 1, padding: 8}}
                      data={[
                          {key: 'Devin'},
                          {key: 'Jackson'},
                          {key: 'James'},
                          {key: 'Joel'},
                          {key: 'John'},
                          {key: 'Jillian'},
                          {key: 'Jimmy'},
                          {key: 'Julie'},
                          {key: 'Devisn'},
                          {key: 'Jacksson'},
                          {key: 'Jamess'},
                          {key: 'Joels'},
                          {key: 'Johns'},
                          {key: 'Jillsian'},
                          {key: 'Jimmsy'},
                          {key: 'Julise'},
                          {key: 'Devdsin'},
                          {key: 'Jacsdkson'},
                          {key: 'Jasdmes'},
                          {key: 'Josdel'},
                          {key: 'Josdhn'},
                          {key: 'Jisdllian'},
                          {key: 'Jimsdmy'},
                          {key: 'Julsdie'},
                          {key: 'Devdqsin'},
                          {key: 'Jacsqdkson'},
                          {key: 'Jasqdmes'},
                          {key: 'Josqdel'},
                          {key: 'Josqdhn'},
                          {key: 'Jisqdllian'},
                          {key: 'Jimsqdmy'},
                          {key: 'Julsqdie'},
                          {key: 'Dasevin'},
                          {key: 'Jacaskson'},
                          {key: 'Jamesas'},
                          {key: 'Joelas'},
                          {key: 'asJohn'},
                          {key: 'Jisallian'},
                          {key: 'Jimmasy'},
                          {key: 'Jsaulie'},
                          {key: 'Defdvisn'},
                          {key: 'Jacgfksson'},
                          {key: 'Jamehgss'},
                          {key: 'Joelshg'},
                          {key: 'Johnshg'},
                          {key: 'Jilhghglsian'},
                          {key: 'Jimmshgy'},
                          {key: 'Julisdfse'},
                          {key: 'Devdsigfdn'},
                          {key: 'Jacsdksfdson'},
                          {key: 'Jasdmefsdfs'},
                          {key: 'Josdefsdfl'},
                          {key: 'Josdhfsdn'},
                          {key: 'Jisdllifsan'},
                          {key: 'Jimsdmfdsy'},
                          {key: 'Julsdisdfe'},
                          {key: 'Devdqsisdfn'},
                          {key: 'Jacsqdkssdfon'},
                          {key: 'Jasqdmesfds'},
                          {key: 'Josqdesfdl'},
                          {key: 'Josqdhfsdfdsn'},
                          {key: 'Jisqdlliafdsn'},
                          {key: 'Jimsqdmfsdy'},
                          {key: 'Julsqdifsde'},
                      ]} renderItem={({item}) => <View style={{height: 40, backgroundColor: 'powderblue'}}>
                <Button
                    onPress={() => { Alert.alert('You tapped ' + item.key)}}
                    title={item.key}
                />
            </View>}
            />
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
    bigblue: {
        color: 'blue',
        fontWeight: 'bold',
        fontSize: 30,
    },
    red: {
        color: 'red',
    },
});
