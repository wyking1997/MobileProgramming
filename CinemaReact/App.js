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

        this.getLoginComponent = this.getLoginComponent.bind(this);
        this.loginSuccess = this.loginSuccess.bind(this);
        this.getCinemasListElementForNonAdminUser = this.getCinemasListElementForNonAdminUser.bind(this);

        // this.initializeData();

        // element = this.getMovieListElement([]);
        element = this.getLoginComponent();
        this.state = {movies: [], cinemas: [], associations: [], element: element, curr_comp: "login"};

        const secondThis = this;
        firebase.database().ref('movies').on('value', (dataSnapshot)=>{
            const x = dataSnapshot.val();
            const ls = [];
            for(var propt in x) ls.push(x[propt]);
            if (secondThis.state.curr_comp != "movieList")
                secondThis.setState({movies: ls});
            else
                secondThis.setState({movies: ls, element: secondThis.getMovieListElement(ls)});
        });
        firebase.database().ref('cinemas').on('value', (dataSnapshot)=>{
            const x = dataSnapshot.val();
            const ls = [];
            for(var propt in x) ls.push(x[propt]);
            if (secondThis.state.curr_comp != "cinemaList")
                secondThis.setState({cinemas: ls});
            else
                secondThis.setState({cinemas: ls, element: secondThis.getCinemasListElement(ls)});
        });
        firebase.database().ref('associations').on('value', (dataSnapshot)=>{
            const x = dataSnapshot.val();
            const ls = [];
            for(var propt in x) ls.push(x[propt]);
            secondThis.setState({associations: ls});
        });


        // const secondThis = this;
        // AsyncStorage.getItem('associations').then(x => {
        //     if (x != undefined){
        //         secondThis.setState({associations: JSON.parse(x)});
        // }});
        //
        // AsyncStorage.getItem('cinemas').then(x => {
        //     if (x == undefined){
        //         let cinemas = JSON.stringify(secondThis.getListOfCinemas());
        //         AsyncStorage.setItem('cinemas', cinemas);
        //         AsyncStorage.setItem('idCinema', '3');
        //         // secondThis.setState({cinemas: this.getListOfCinemas()});
        //     } else if (this.state.curr_comp == "cinemaList") {
        //         secondThis.setState({cinemas: JSON.parse(x), element: secondThis.getCinemasListElement(JSON.parse(x))});
        //     } else
        //         secondThis.setState({cinemas: JSON.parse(x)});
        // });
        // AsyncStorage.getItem('movies').then(x => {
        //     if (x == undefined){
        //         AsyncStorage.setItem('movies', JSON.stringify([]));
        //         AsyncStorage.setItem('id', '0');
        //         secondThis.setState({movies: JSON.parse(x), element: secondThis.getMovieListElement(JSON.parse(x))});
        //     } else if (this.state.curr_comp == "movieList")
        //         secondThis.setState({movies: JSON.parse(x), element: secondThis.getMovieListElement(JSON.parse(x))});
        //     else
        //         secondThis.setState({movies: JSON.parse(x)})
        // });
    }











    getLoginComponent(){
        return <LoginComponent login={this.loginSuccess}/>;
    }

    loginSuccess(email){
        if (email == "t@test.gmail")
            this.setCinemaListView();
        else {
            this.setCinemaListViewForNormalUser();
        }
    }










    setCinemaListViewForNormalUser(){
        let el = this.getCinemasListElementForNonAdminUser(this.state.cinemas);
        this.setState({element: el, curr_comp: "scinema_list"})
    }
    setCinemaDetailForNormalUser(cinemaKey){
        cinema = this.state.cinemas.find(m => m.key === cinemaKey);
        newElement = this.getCinemaDetailComponentForSimpleUser(cinema)
        this.setState({element: newElement});
    }
    getCinemaDetailComponentForSimpleUser(cinema){
        let assoc = this.state.associations;
        assoc = assoc.filter(x => x.cinema_key == cinema.key);
        return (<CinemaDetailComponent cinema={cinema} chartData={assoc} onComeBack={() => {this.setCinemaListViewForNormalUser()}} />);
    }
    getCinemasListElementForNonAdminUser(cinemas){
        myCinemas = cinemas;
        return (<View style={styles.mainView}>
            <FlatList
                style={styles.listView}
                data={myCinemas}
                renderItem={({item}) =>
                    <TouchableHighlight onPress={() => {this.setCinemaDetailForNormalUser(item.key)}} underlayColor="azure">
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
    setCinemaDetailView(cinemaKey){
        cinema = this.state.cinemas.find(m => m.key === cinemaKey);
        newElement = this.getCinemaDetailComponent(cinema)
        this.setState({element: newElement});
    }
    setCreateNewCinema(){

    }
    getCinemaDetailComponent(cinema){
        let assoc = this.state.associations;
        assoc = assoc.filter(x => x.cinema_key == cinema.key);
        return (<CinemaDetailComponent cinema={cinema} chartData={assoc} onComeBack={() => {this.setCinemaListView()}} />);
    }
    getCinemasListElement(cinemas){
        myCinemas = cinemas;
        return (<View style={styles.mainView}>
            <FlatList
                style={styles.listView}
                data={myCinemas}
                renderItem={({item}) =>
                    <TouchableHighlight onPress={() => {this.setCinemaDetailView(item.key)}} underlayColor="azure">
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
        // list.map(x => x.key = x.key);
        // return list;
    }




















    handleAddNewMovie(movie){
        let tt = this;

        let key = firebase.database().ref().child('movies').push().key;
        firebase.database().ref('movies').child(key).update({
            key: key,
            title: movie.title,
            date: movie.date,
            duration: movie.duration,
            description: movie.description
        });

        movie.key = key;
        newMovies = tt.state.movies.concat(movie);
        this.setState({
            movies: newMovies,
            element: this.getMovieListElement(newMovies)
        });
        // AsyncStorage.getItem("id").then(v =>{
        //     movie.id = parseInt(v);
        //     newMovies = tt.state.movies.concat(movie);
        //     AsyncStorage.setItem('movies',JSON.stringify(newMovies));
        //     AsyncStorage.setItem('id', (parseInt(v) + 1) + "");
        //     this.setState({
        //         movies: newMovies,
        //         element: this.getMovieListElement(newMovies)
        //     });
        // });
    }

    handleUpdate(movie){
        newMovies = this.state.movies;
        newMovies[newMovies.findIndex(el => el.key === movie.key)] = movie;
        firebase.database().ref('movies').child(movie.key).update({
            key: movie.key,
            title: movie.title,
            date: movie.date,
            duration: movie.duration,
            description: movie.description
        });
        this.setState({movies: newMovies, element: this.getMovieListElement(newMovies)});
    }

    handleDelete(movieKey){
        firebase.database().ref().child('movies').child(movieKey).remove();
        newMovies = this.state.movies;
        newMovies = newMovies.filter(x => x.key != movieKey);
        AsyncStorage.setItem('movies',JSON.stringify(newMovies));
        this.setState({movies: newMovies, element: this.getMovieListElement(newMovies)});
    }

    setDetailView(movieKey){
        movie = this.state.movies.find(m => m.key === movieKey);
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
        myMovies = movies;
        return (<View style={styles.mainView}>
            <FlatList
                style={styles.listView}
                data={myMovies}
                renderItem={({item}) =>
                    <TouchableHighlight onPress={() => {this.setDetailView(item.key)}} underlayColor="azure">
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
        // list.map(x => x.key = x.id);
        // return list;
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
