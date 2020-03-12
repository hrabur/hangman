import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

class App extends React.Component {
  state = {
    game: null
  };

  startNewGame = async () => {
    const response = await fetch("http://localhost:8080/api/games", {
      method: "POST",
      cache: "no-cache"
    });
    let game = await response.json();
    this.setState(prev => ({ game }));
  };

  render() {
    return !this.state.game ? (
      <div className="App">
        <h1>Welcome to OMG Hangman!!!</h1>
        <button onClick={this.startNewGame}>Hang the man</button>
      </div>
    ) : (
      <div className="App">
        <h1>{this.state.game.maskedWord}</h1>
        {this.state.game.remaining.map(letter => (
          <button className="btn btn-link">
            <span>{letter}</span>
          </button>
        ))}
      </div>
    );
  }
}

export default App;
