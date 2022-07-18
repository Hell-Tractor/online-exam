import { post } from '@/utils/request'

export default {
  selectQuestonByCondition: query => post('/api/admin/question/page', query),
  editQuestion: query => post('/api/admin/question/edit', query),
  select: id => post('/api/admin/question/select/' + id),
  deleteQuestion: id => post('/api/admin/question/delete/' + id)
}
