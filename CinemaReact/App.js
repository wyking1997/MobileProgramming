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
import CinemaDetailComponent from "./CinemaDetailComponent";
import LoginComponent from "./LoginComponent";
import firebase from 'react-native-firebase';

// react-native run-android
// ctrl + M while in the app in simulator for debugg
// Alert.alert('You tapped ' + item.title)
// adb reverse tcp:8081 tpc:8081 in the cmd of
//              C:\Users\marius\AppData\Local\Android\sdk\platform-tools after phone is connected to the pc

export default class App extends Component<{}> {

    constructor(props){
        super(props);

        // cinemaList
        // login
        // movieList
        // createMovie
        // scinema_list => cinema list for simple user

        this.setDetailView = this.setDetailView.bind(this);
        this.getMovieListElement = this.getMovieListElement.bind(this);
        this.setCinemaDetailView = this.setCinemaDetailView.bind(this);
        this.setCreateNewMovie = this.setCreateNewMovie.bind(this);
        this.handleUpdate = this.handleUpdate.bind(this);
        this.getMovieDetailComponent = this.getMovieDetailComponent.bind(this);
        this.handleAddNewMovie = this.handleAddNewMovie.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.setCinemaListView = this.setCinemaListView.bind(this);
        this.getCinemasListElement = this.getCinemasListElement.bind(this);
        this.getCinemaForFlatList = this.getCinemaForFlatList.bind(this);
        this.getCinemaDetailComponent = this.getCinemaDetailComponent.bind(this);
        this.setCreateNewCinema = this.setCreateNewCinema.bind(this);
        this.getCinemaDetailComponentForSimpleUser = this.getCinemaDetailComponentForSimpleUser.bind(this);
        this.setCinemaDetailForNormalUser = this.setCinemaDetailForNormalUser.bind(this);
        this.setCinemaListViewForNormalUser = this.setCinemaListViewForNormalUser.bind(this);

        this.getListOfCinemasHardCode = this.getListOfCinemasHardCode.bind(this);
        this.getListOfFilmsHardCode = this.getListOfFilmsHardCode.bind(this);
        this.getAssociationsHardCode = this.getAssociationsHardCode.bind(this);
        this.getLoginComponent = this.getLoginComponent.bind(this);
        this.loginSuccess = this.loginSuccess.bind(this);
        this.getCinemasListElementForNonAdminUser = this.getCinemasListElementForNonAdminUser.bind(this);

        // this.initializeData();

        // element = this.getMovieListElement([]);
        element = this.getLoginComponent();
        this.state = {movies: [], cinemas: [], associations: [], element: element, curr_comp: "login"};

        const secondThis = this;
        AsyncStorage.getItem('associations').then(x => {
            if (x != undefined){
                secondThis.setState({associations: JSON.parse(x)});
        }});

        AsyncStorage.getItem('cinemas').then(x => {
            if (x == undefined){
                let cinemas = JSON.stringify(secondThis.getListOfCinemas());
                AsyncStorage.setItem('cinemas', cinemas);
                AsyncStorage.setItem('idCinema', '3');
                // secondThis.setState({cinemas: this.getListOfCinemas()});
            } else if (this.state.curr_comp == "cinemaList") {
                secondThis.setState({cinemas: JSON.parse(x), element: secondThis.getCinemasListElement(JSON.parse(x))});
            } else
                secondThis.setState({cinemas: JSON.parse(x)});
        });
        AsyncStorage.getItem('movies').then(x => {
            if (x == undefined){
                AsyncStorage.setItem('movies', JSON.stringify([]));
                AsyncStorage.setItem('id', '0');
                secondThis.setState({movies: JSON.parse(x), element: secondThis.getMovieListElement(JSON.parse(x))});
            } else if (this.state.curr_comp == "movieList")
                secondThis.setState({movies: JSON.parse(x), element: secondThis.getMovieListElement(JSON.parse(x))});
            else
                secondThis.setState({movies: JSON.parse(x)})
        });
    }











    getLoginComponent(){
        return <LoginComponent login={this.loginSuccess}/>;
    }

    loginSuccess(email){
        if (email == "marius.comiati97@gmail.com")
            this.setCinemaListView();
        else {
            this.setCinemaListViewForNormalUser();
        }
    }










    setCinemaListViewForNormalUser(){
        let el = this.getCinemasListElementForNonAdminUser(this.state.cinemas);
        this.setState({element: el, curr_comp: "scinema_list"})
    }
    setCinemaDetailForNormalUser(cinemaId){
        cinema = this.state.cinemas.find(m => m.id === cinemaId);
        newElement = this.getCinemaDetailComponentForSimpleUser(cinema)
        this.setState({element: newElement});
    }
    getCinemaDetailComponentForSimpleUser(cinema){
        let assoc = this.state.associations;
        assoc = assoc.filter(x => x.cinema_id == cinema.id);
        return (<CinemaDetailComponent cinema={cinema} chartData={assoc} onComeBack={() => {this.setCinemaListViewForNormalUser()}} />);
    }
    getCinemasListElementForNonAdminUser(cinemas){
        myCinemas = this.getCinemaForFlatList(cinemas);
        return (<View style={styles.mainView}>
            <FlatList
                style={styles.listView}
                data={myCinemas}
                renderItem={({item}) =>
                    <TouchableHighlight onPress={() => {this.setCinemaDetailForNormalUser(item.id)}} underlayColor="azure">
                        <View style={styles.listItemView}>
                            <Text style={styles.bigBlack}>
                                {item.name + "\n" + item.adress + "\n" + item.phoneNumber}
                            </Text>
                        </View>
                    </TouchableHighlight>
                }
            />
        </View>);
    }













    setCinemaListView(){
        this.setState({element: this.getCinemasListElement(this.state.cinemas), curr_comp: "cinemaList"});
    }
    setCinemaDetailView(cinemaId){
        cinema = this.state.cinemas.find(m => m.id === cinemaId);
        newElement = this.getCinemaDetailComponent(cinema)
        this.setState({element: newElement});
    }
    setCreateNewCinema(){

    }
    getCinemaDetailComponent(cinema){
        let assoc = this.state.associations;
        assoc = assoc.filter(x => x.cinema_id == cinema.id);
        return (<CinemaDetailComponent cinema={cinema} chartData={assoc} onComeBack={() => {this.setCinemaListView()}} />);
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
    getCinemaForFlatList(list){
        list.map(x => x.key = x.id);
        return list;
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

    setMovieListView(){
        this.setState({element: this.getMovieListElement(this.state.movies), curr_comp: "movieList"});
    }

    setCreateNewMovie(){
        this.setState({element: this.getAddMovieComponent(), curr_comp: "createMovie"});
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

    getMovieForFlatList(list){
        list.map(x => x.key = x.id);
        return list;
    }














    initializeData(){
        cinemas = this.getListOfCinemas();
        movies = this.getListOfFilms();
        associations = this.getAssociations();
        AsyncStorage.setItem('cinemas',JSON.stringify(cinemas));
        AsyncStorage.setItem('movies',JSON.stringify(movies));
        AsyncStorage.setItem('associations',JSON.stringify(associations));
    }

    getListOfCinemasHardCode(){
        return [
            {id: 0, name: "Cinema Marasti", adress: "Strada Aurel Vlaicu 3", phoneNumber: "0264 598 784"},
            {id: 1, name: "Cinema Victoria", adress: "Bulevardul Eroilor 51", phoneNumber: "0264 450 143"},
            {id: 2, name: "Cinema Florin Piersic", adress: "Piața Mihai Viteazu 11", phoneNumber: "0264 433 477"}
        ];
    }

    getListOfFilmsHardCode(){
        return [
            {id: 0, date: '1995-02-21', title: "Piratii din caraibe", duration: 95, description: "betiv norocos"},
            {id: 1, date: '2010-06-15', title: "Omul paianjen", duration: 80, description: "This is with benner!"},
            {id: 2, date: '2014-02-16', title: "Thor", duration: 115, description: "big green animal"},
            {id: 3, date: '2011-11-02', title: "Neinfricata", duration: 65, description: "roscata si trage cu arcul"},
            {id: 4, date: '2017-01-01', title: "Minionii 3", duration: 87, description: "galbeni si multi 1"},
        ];
    }

    getAssociationsHardCode(){
        return [
            {id: 0, cinema_id: 0, movie_id: 0, date: '2017-12-01'},
            {id: 1, cinema_id: 0, movie_id: 0, date: '2017-12-02'},
            {id: 2, cinema_id: 0, movie_id: 0, date: '2017-12-03'},
            {id: 3, cinema_id: 0, movie_id: 2, date: '2017-12-02'},
            {id: 4, cinema_id: 0, movie_id: 2, date: '2017-12-03'},
            {id: 5, cinema_id: 0, movie_id: 3, date: '2017-12-03'},
            {id: 6, cinema_id: 0, movie_id: 4, date: '2017-12-01'},
            {id: 7, cinema_id: 0, movie_id: 4, date: '2017-12-02'},
            {id: 8, cinema_id: 0, movie_id: 4, date: '2017-12-03'},
            {id: 9, cinema_id: 1, movie_id: 1, date: '2017-12-01'},
            {id: 10, cinema_id: 1, movie_id: 1, date: '2017-12-01'},
            {id: 11, cinema_id: 1, movie_id: 1, date: '2017-12-02'},
            {id: 12, cinema_id: 1, movie_id: 1, date: '2017-12-02'},
            {id: 13, cinema_id: 1, movie_id: 1, date: '2017-12-03'},
            {id: 14, cinema_id: 1, movie_id: 1, date: '2017-12-04'},
            {id: 15, cinema_id: 1, movie_id: 2, date: '2017-12-02'},
            {id: 16, cinema_id: 1, movie_id: 2, date: '2017-12-04'},
            {id: 17, cinema_id: 2, movie_id: 0, date: '2017-12-01'},
            {id: 18, cinema_id: 2, movie_id: 0, date: '2017-12-02'},
            {id: 19, cinema_id: 2, movie_id: 3, date: '2017-12-02'},
            {id: 20, cinema_id: 2, movie_id: 3, date: '2017-12-02'},
            {id: 21, cinema_id: 2, movie_id: 3, date: '2017-12-03'},
            {id: 22, cinema_id: 2, movie_id: 3, date: '2017-12-01'},
            {id: 23, cinema_id: 2, movie_id: 3, date: '2017-12-01'},
            {id: 24, cinema_id: 2, movie_id: 4, date: '2017-12-01'},
            {id: 25, cinema_id: 2, movie_id: 4, date: '2017-12-02'},
            {id: 26, cinema_id: 2, movie_id: 4, date: '2017-12-03'},
            {id: 27, cinema_id: 2, movie_id: 4, date: '2017-12-04'}
        ];
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
