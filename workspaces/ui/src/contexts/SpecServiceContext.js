import React from 'react'
import {GenericContextFactory} from './GenericContextFactory';
import {NavigationContext} from './NavigationContext';

const {
  Context: SpecServiceContext,
  withContext: withSpecServiceContext
} = GenericContextFactory(null);

class SpecServiceStore extends React.Component {

  state = {
    apiName: ''
  }

  componentDidMount() {
    const {specService} = this.props;
    specService.getConfig().then(({config}) => this.setState({apiName: config.name}))
  }

  render() {
    const {specService} = this.props;

    return (
      <SpecServiceContext.Provider value={{specService, apiName: this.state.apiName}}>
        {this.props.children}
      </SpecServiceContext.Provider>
    );
  }
}

export {
  withSpecServiceContext,
  SpecServiceContext,
  SpecServiceStore
};
