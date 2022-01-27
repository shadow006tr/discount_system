const {useEffect, useState} = require("react");

const useBeforeRender = (callback, deps) => {
    const [isRun, setIsRun] = useState(false);
    const depsArray = new Array(deps)

    if (!isRun) {
        callback();
        setIsRun(true);
    }

    useEffect(() => () => setIsRun(false), depsArray);
};

export default useBeforeRender;