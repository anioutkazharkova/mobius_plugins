### 

```
interface IPresenter

abstract class BasePresenter<T : IView> :
IPresenter {
open var view: T? = null

    fun onAttachView(view: T) {
        this.view = view
    }

    fun onDetachView() {
        this.view = null
    }
}

interface IInteractor {
    fun setup(di: DIManager)

    fun attachView()

    fun detachView()
}

interface IView : IInputView

interface IInputView

abstract class BaseInteractor<T : IView>(private val coroutineContext: CoroutineContext) {
    protected lateinit var scope: ModuleCoroutineScope

    fun attachView() {
        scope = ModuleCoroutineScope(coroutineContext)
    }

    fun detachView() {
        scope.viewDetached()
    }
}

interface IConfigurator {
    fun create(view: IView): IInteractor?
}

class ModuleConfig {
    companion object {
        val instance = ModuleConfig()
    }

    fun config(view: IView): com.azharkova.kmmdi.shared.base.IInteractor? {
        if (view is IMoviesListView) {
            return MoviesListConfigurator.instance.create(view)
        }
        return null
    }
}

class MoviesListConfigurator : IConfigurator {
    companion object {
        val instance = MoviesListConfigurator()
    }

    override fun create(view: IView): IInteractor? {
        val interactor: IMoviesListInteractor =
            MoviesListInteractor()
        val presenter = MoviesListPresenter()
        interactor.presenter = presenter
        presenter.view = view as? IMoviesListView
        return interactor
    }
}
```

