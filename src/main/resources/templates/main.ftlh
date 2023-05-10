<#import "parts/commonPart.ftlh" as c>
<@c.page>
    <form method="get" action="/main">
        <div class="row p-2 g-3 align-items-center">
            <div class="col-auto">
                <input class="form-control"
                       type="text"
                       name="tagFilter"
                       id="inputTagFilter"
                       value="${tagFilter!}"
                       placeholder="Поиск по тегу">
            </div>
            <div class="col-auto">
                <button type="submit"
                        class="btn btn-warning">Найти
                </button>
            </div>
        </div>
    </form>

    <div class="my-2">
        <a class="btn btn-warning"
           data-bs-toggle="collapse"
           href="#collapseExample"
           role="button"
           aria-expanded="false"
           aria-controls="collapseExample">Добавить сообщение
        </a>
    </div>

    <div class="collapse" id="collapseExample">
        <form method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <input class="form-control"
                       type="text"
                       name="messageText"
                       id="inputMessageText"
                       placeholder="Введите сообщение"/>
            </div>
            <div class="mb-3">
                <input class="form-control"
                       type="text"
                       name="messageTag"
                       id="inputMessageTag"
                       placeholder="Тег">
            </div>
            <div class="mb-3">
                <input class="form-control"
                       type="file"
                       name="messageFile"
                       id="inputMessageFile"
                       placeholder="Файл с изображением">
            </div>
            <div class="mb-3">
                <button type="submit"
                        class="btn btn-warning">
                    Добавить
                </button>
            </div>
        </form>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list dgMessages as message>
            <div class="col">
                <div class="card h-100">
                    <div class="card-body">
                        <#if message.filename??>
                            <img class="img-thumbnail"
                                 src="/img/${message.filename}"
                                 alt=""
                            >
                        </#if>
                        <p class="card-text">${message.text}</p>
                        <span class="badge text-bg-secondary">${message.tag!}</span>
                    </div>
                    <div class="card-footer text-muted">
                        ${message.author.getLogin()}
                    </div>
                </div>
            </div>
        <#else>
            Нет сообщений
        </#list>
    </div>
</@c.page>