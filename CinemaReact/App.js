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

        this.setDetailView = this.setDetailView.bind(this);
        this.getMovieListElement = this.getMovieListElement.bind(this);
        this.setCinemaDetailView = this.setCinemaDetailView.bind(this);
        this.setCreateNewMovie = this.setCreateNewMovie.bind(this);
        this.handleUpdate = this.handleUpdate.bind(this);
        this.getMovieDetailComponent = this.getMovieDetailComponent.bind(this);
        this.handleAddNewMovie = this.handleAddNewMovie.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.setCinemaListView = this.setCinemaListView.bind(this);
        this.getListOfCinemas = this.getListOfCinemas.bind(this);
        this.getCinemasListElement = this.getCinemasListElement.bind(this);
        this.getCinemaForFlatList = this.getCinemaForFlatList.bind(this);
        this.getCinemaDetailComponent = this.getCinemaDetailComponent.bind(this);
        this.setCreateNewCinema = this.setCreateNewCinema.bind(this);

        element = this.getMovieListElement([]);
        this.state = {movies: [], cinemas: [], element: element};

        const secondThis = this;
        AsyncStorage.getItem('cinemas').then(x => {
            if (x == undefined){
                let cinemas = JSON.stringify(secondThis.getListOfCinemas());
                AsyncStorage.setItem('cinemas', cinemas);
                AsyncStorage.setItem('idCinema', '3');
                secondThis.setState({cinemas: this.getListOfCinemas()});
            } else
                secondThis.setState({cinemas: JSON.parse(x)});
        });
        AsyncStorage.getItem('movies').then(x => {
            if (x == undefined){
                AsyncStorage.setItem('movies', JSON.stringify([]));
                AsyncStorage.setItem('id', '0');
                secondThis.setState({movies: JSON.parse(x), element: secondThis.getMovieListElement(JSON.parse(x))});
            } else
                secondThis.setState({movies: JSON.parse(x), element: secondThis.getMovieListElement(JSON.parse(x))});
        });
    }

    getListOfCinemas(){
        return [
            {id: 0, name: "Cinema Marasti", adress: "Strada Aurel Vlaicu 3", phoneNumber: "0264 598 784"},
            {id: 1, name: "Cinema Victoria", adress: "Bulevardul Eroilor 51", phoneNumber: "0264 450 143"},
            {id: 2, name: "Cinema Florin Piersic", adress: "Piața Mihai Viteazu 11", phoneNumber: "0264 433 477"}
        ];
    }

    setCinemaListView(){
        this.setState({element: this.getCinemasListElement(this.state.cinemas)});
    }

    handleAddNewMovie(movie){
        let tt = this;
        AsyncStorage.getItem("id").then(v =>{
            movie.id = parseInt(v);
            newMovies = tt.state.movies.concat(movie);
            AsyncStorage.setItem('movies',JSON.stringify(newMovies));
            AsyncStorage.setItem('id', (parseInt(v) + 1) + "");
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
        this.setState({movies: newMovies, element: this.getMovieListElement(newMovies)});
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

    setCinemaDetailView(cinemaId){
        cinema = this.state.cinemas.find(m => m.id === cinemaId);
        newElement = this.getCinemaDetailComponent(cinema)
        this.setState({element: newElement});
    }

    setMovieListView(){
        this.setState({element: this.getMovieListElement(this.state.movies)})
    }

    setCreateNewMovie(){
        this.setState({element: this.getAddMovieComponent()});
    }

    setCreateNewCinema(){

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
                                {item.title + " " + item.date + "\n" + item.duration + " minutes"}
                            </Text>
                        </View>
                    </TouchableHighlight>
                }
            />
            <Button
                title="Create New Movie"
                onPress={() => {this.setCreateNewMovie()}}
            />
            <Button
                title="Go to see cinemas"
                onPress={() => {this.setCinemaListView()}}
            />
        </View>);
    }

    getCinemasListElement(cinemas){
        myCinemas = this.getCinemaForFlatList(cinemas);
        return (<View style={styles.mainView}>
            <FlatList
                style={styles.listView}
                data={myCinemas}
                renderItem={({item}) =>
                    <TouchableHighlight onPress={() => {this.setCinemaDetailView(item.id)}} underlayColor="azure">
                        <View style={styles.listItemView}>
                            <Text style={styles.bigBlack}>
                                {item.name + "\n" + item.adress + "\n" + item.phoneNumber}
                            </Text>
                        </View>
                    </TouchableHighlight>
                }
            />
            <Button
                title="Create New Cinema"
                onPress={() => {this.setCreateNewCinema()}}
            />
            <Button
                title="Go to see movies"
                onPress={() => {this.setMovieListView()}}
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

    getCinemaDetailComponent(cinema){
        return null;
    }

    getAddMovieComponent(){
        return <AddNewMovieComponent
            onAdd={this.handleAddNewMovie}
            onComeBack={() => {this.setMovieListView()}}
        />;
    }

    getMovieForFlatList(list){
        list.map(x => x.key = x.id);
        return list;
    }

    getCinemaForFlatList(list){
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
