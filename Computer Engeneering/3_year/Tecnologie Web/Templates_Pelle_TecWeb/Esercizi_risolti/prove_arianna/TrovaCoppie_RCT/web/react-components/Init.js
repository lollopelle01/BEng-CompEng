'use strict';

class Init extends React.Component {

    render() {
    return (
        <div className="init">
        	START: <input type="button" name="start" onClick={this.props.start}/><br/><br/>
        </div>
        );
    }
}