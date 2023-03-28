<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Secured</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/app/css/styles.css">
</head>
<body>
    <div id="wrapper">
        <h1>Secured</h1>
        <p><textarea cols="100" rows="10">${token}</textarea></p>
        <p><textarea cols="100" rows="10">${context}</textarea></p>
        <p><textarea cols="100" rows="10">${accessToken}</textarea></p>
    </div>
</body>
</html>
