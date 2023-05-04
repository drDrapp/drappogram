<#import "parts/commonPart.ftlh" as c>
<@c.page>
    <form method="get" action="/main" class="row">
        <div class="col-6">
            <label>
                <input type="text" name="tagFilter" class="form-control" value="${tagFilter!}"
                       placeholder="Search by tag">
            </label>
        </div>
        <div class="col-2">
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </div>
    </form>

    <div class="mb-3">
        <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
           aria-controls="collapseExample">
            Add new Message
        </a>
    </div>
    <div class="collapse" id="collapseExample">
        <div class="mb-3">
            <label>
                <input type="text" class="form-control" name="text" placeholder="Введите сообщение"/>
            </label>
        </div>
        <div class="mb-3">
            <label>
                <input type="text" class="form-control" name="tag" placeholder="Тэг">
            </label>
        </div>
        <div class="mb-3">
            <input class="form-control" type="file" name="messageFile" placeholder="Файл с изображением">
        </div>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list dgMessages as message>
            <div class="col">
                <div class="card h-60">
                    <svg class="bd-placeholder-img card-img-top" width="100%" height="180"
                         xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Image cap"
                         preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title>
                        <rect width="100%" height="100%" fill="#868e96"></rect>
                        <text x="50%" y="50%" fill="#dee2e6" dy=".3em">Image cap</text>
                    </svg>
                    <#--                <#if message.filename??>-->
                    <#--                    <img src="/img/${message.filename}" class="card-img-top">-->
                    <#--                </#if>-->
                    <div class="card-body">
                        <h5 class="card-title">Card title</h5>
                        <p class="card-text">${message.text}</p>
                    </div>
                    <div class="card-link">
                        <p class="card-text">${message.tag}</p>
                    </div>
                    <div class="card-footer text-muted">
                        ${message.author.getLogin()}
                    </div>
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>