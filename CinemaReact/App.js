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

// react-native run-android
// Alert.alert('You tapped ' + item.title)

export default class App extends Component<{}> {

    constructor(props){
        super(props);

        this.getMovies = this.getMovies.bind(this);
        this.setDetailView = this.setDetailView.bind(this);
        this.getMovieListElement = this.getMovieListElement.bind(this);
        this.setDetailView = this.setDetailView.bind(this);
        this.setCreateNewMovie = this.setCreateNewMovie.bind(this);

        movies = this.getMovies();
        element = this.getMovieListElement(movies);
        this.state = {movies: movies, element: element};
    }

    setDetailView(movieId){
        movie = this.state.movies.find(m => m.id === movieId);
        Alert.alert("" + movie);
    }

    setMovieListView(){
        this.setState(this.getMovieListElement);
    }

    setCreateNewMovie(){

    }

    getMovieListElement(movies){
        myMovies = this.getMovieForFlatList(movies);
        return (<View style={styles.mainView}>
            <FlatList
                style={styles.listView}
                data={myMovies}
                renderItem={({item}) =>
                    <TouchableHighlight onPress={() => {}} underlayColor="azure">
                        <View style={styles.listItemView}>
                            <Text style={styles.bigBlack}>
                                {item.title + " " + item.year + "\n" + item.duration + " minutes"}
                            </Text>
                        </View>
                    </TouchableHighlight>
                }
            />
            <Button
                syle={{padding: 10}}
                title="Create New Movie"
                onPress={() => {}}
            />
        </View>);
    }

    getMovies(){
        return [
            {id: 0, year: 2017, title: "Piratii din caraibe", duration: 95, description: "betiv norocos"},
            {id: 1, year: 2017, title: "Omul paianjen", duration: 80, description: "This is with benner!"},
            {id: 2, year: 2017, title: "Thor", duration: 115, description: "big green animal"},
            {id: 3, year: 2008, title: "Neinfricata", duration: 65, description: "roscata si trage cu arcul"},
            {id: 4, year: 2010, title: "Minionii 1", duration: 87, description: "galbeni si multi 1"},
            {id: 5, year: 2011, title: "Minionii 2", duration: 71, description: "galbeni si multi 2"},
            {id: 6, year: 2017, title: "Piratii din caraibe", duration: 95, description: "betiv norocos"},
            {id: 7, year: 2017, title: "Omul paianjen", duration: 80, description: "This is with benner!"},
            {id: 8, year: 2017, title: "Thor", duration: 115, description: "big green animal"},
            {id: 9, year: 2008, title: "Neinfricata", duration: 65, description: "roscata si trage cu arcul"},
            {id: 10, year: 2010, title: "Minionii 1", duration: 87, description: "galbeni si multi 1"},
            {id: 11, year: 2011, title: "Minionii 2", duration: 71, description: "galbeni si multi 2"},
        ];
    }

    getMovieForFlatList(list){
        list.map(x => x.key = x.id);
        return list;
    }

    render() {
        return this.state.movies === null ? null : (
            this.state.element
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
