import {useRoute, useRouter} from 'vue-router'
import type { RouteLocationRaw } from 'vue-router'

export function useNavigation() {
    const router = useRouter()
    const route = useRoute()

    /**
     * @param to  either a string path or a router location object
     */
    const navigateTo = (to: string | RouteLocationRaw) => {
        if (!to) {
            console.error('No link provided')
            return
        }
        router.push(to).catch(err => {
            // avoid uncaught promise if navigation is aborted
            if (err.name !== 'NavigationDuplicated') console.error(err)
        })
    }

    /**
     * Returns the "id" param from the current route
     */
    const getIdFromUrl = (paramName: string): number | undefined => {
        const id = route.params[paramName] as string
        console.log("id", route.params)
        if (typeof id === 'string') {
            const parsed = parseInt(id, 10)
            return isNaN(parsed) ? undefined : parsed
        }
        return undefined
    }

    /**
     * Sets the "id" param to the current route
     */
    const setIdToUrl = (id: number, routingName: string) => {
        router.replace({
            name: routingName,
            params: {
                libraryId: id.toString()
            }
        })
    }

    return {
        navigateTo,
        getIdFromUrl,
        setIdToUrl
    }
}
