import { useRouter } from 'vue-router'
import type { RouteLocationRaw } from 'vue-router'

export function useNavigation() {
    const router = useRouter()

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

    return { navigateTo }
}
