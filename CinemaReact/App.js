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
    Alert, TouchableHighlight, FlatList,
    AsyncStorage
} from 'react-native';
import MovieDetailComponent from "./MovieDetailComponent";
import AddNewMovieComponent from "./AddNewMovieComponent";

// react-native run-android
// ctrl + M while in the app in simulator for debugg
// Alert.alert('You tapped ' + item.title)
// adb reverse tcp:8081 tpc:8081 in the cmd of
//              C:\Users\marius\AppData\Local\Android\sdk\platform-tools after phone is connected to the pc

export default class App extends Component<{}> {

    constructor(props){
        super(props);

        this.getMovies = this.getMovies.bind(this);
        this.setDetailView = this.setDetailView.bind(this);
        this.getMovieListElement = this.getMovieListElement.bind(this);
        this.setDetailView = this.setDetailView.bind(this);
        this.setCreateNewMovie = this.setCreateNewMovie.bind(this);
        this.handleUpdate = this.handleUpdate.bind(this);
        this.getMovieDetailComponent = this.getMovieDetailComponent.bind(this);
        this.handleAddNewMovie = this.handleAddNewMovie.bind(this);
        this.handleDelete = this.handleDelete.bind(this);

        element = this.getMovieListElement([]);
        this.state = {movies: [], element: element};

        const secondThis = this;
        AsyncStorage.getItem('movies').then(x => {
            console.log("bau " + x);
            if (x == undefined){
                AsyncStorage.setItem('movies', JSON.stringify([]));
                AsyncStorage.setItem('id', '0');
                secondThis.setState({movies: JSON.parse(x), element: secondThis.getMovieListElement(JSON.parse(x))});
            } else
                secondThis.setState({movies: JSON.parse(x), element: secondThis.getMovieListElement(JSON.parse(x))});
        });
    }

    handleAddNewMovie(movie){
        let tt = this;
        AsyncStorage.getItem("id").then(v =>{
            movie.id = parseInt(v);
            newMovies = tt.state.movies.concat(movie);
            AsyncStorage.setItem('movies',JSON.stringify(newMovies));
            AsyncStorage.setItem('id', (v + 1) + "");
            this.setState({
                movies: newMovies,
                element: this.getMovieListElement(newMovies)
            });
        });
    }

    handleUpdate(movie){
        newMovies = this.state.movies;
        newMovies[newMovies.findIndex(el => el.id === movie.id)] = movie;
        AsyncStorage.setItem('movies',JSON.stringify(newMovies));
        this.setState({movies: newMovies, element: this.getMovieListElement(this.state.movies)});
    }

    handleDelete(movieId){
        newMovies = this.state.movies;
        newMovies = newMovies.filter(x => x.id != movieId);
        AsyncStorage.setItem('movies',JSON.stringify(newMovies));
        this.setState({movies: newMovies, element: this.getMovieListElement(newMovies)});
    }

    setDetailView(movieId){
        movie = this.state.movies.find(m => m.id === movieId);
        newElement = this.getMovieDetailComponent(movie)
        this.setState({element: newElement});
    }

    setMovieListView(){
        this.setState({element: this.getMovieListElement(this.state.movies)})
    }

    setCreateNewMovie(){
        this.setState({element: this.getAddMovieComponent()});
    }

    getMovieListElement(movies){
        myMovies = this.getMovieForFlatList(movies);
        return (<View style={styles.mainView}>
            <FlatList
                style={styles.listView}
                data={myMovies}
                renderItem={({item}) =>
                    <TouchableHighlight onPress={() => {this.setDetailView(item.id)}} underlayColor="azure">
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
                onPress={() => {this.setCreateNewMovie()}}
            />
        </View>);
    }

    getMovieDetailComponent(movie){
        return <MovieDetailComponent
            movie={movie}
            onUpdate={this.handleUpdate}
            onDelete={this.handleDelete}
            onComeBack={() => {this.setMovieListView()}}
        />;
    }

    getAddMovieComponent(){
        return <AddNewMovieComponent
            onAdd={this.handleAddNewMovie}
            onComeBack={() => {this.setMovieListView()}}
        />;
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
